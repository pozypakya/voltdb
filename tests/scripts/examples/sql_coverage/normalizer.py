#!/usr/bin/env python

# This file is part of VoltDB.
# Copyright (C) 2008-2015 VoltDB Inc.
#
# Permission is hereby granted, free of charge, to any person obtaining
# a copy of this software and associated documentation files (the
# "Software"), to deal in the Software without restriction, including
# without limitation the rights to use, copy, modify, merge, publish,
# distribute, sublicense, and/or sell copies of the Software, and to
# permit persons to whom the Software is furnished to do so, subject to
# the following conditions:
#
# The above copyright notice and this permission notice shall be
# included in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
# IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
# OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
# ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
# OTHER DEALINGS IN THE SOFTWARE.

import sys
# For spot testing, add the path to SQLCoverageReport, just based on knowing
# where we are now
sys.path.append('../../')
sys.path.append('../../../../lib/python')

import decimal
import math
import re
import types

from voltdbclient import FastSerializer

from SQLCoverageReport import generate_html_reports

# A compare function which can handle datetime to None comparisons
# -- where the standard cmp gets a TypeError.
def safecmp(x, y):
    # iterate over lists -- just like cmp does,
    # but compare in a way that avoids a TypeError
    # when a None value and datetime are corresponding members of the list.
    for (xn, yn) in zip(x, y):
        if xn is None:
            if yn is None:
                continue
            return -1
        if yn is None:
            return 1
        rn = cmp(xn, yn)
        if rn:
            return rn  # return first difference
    # With all elements the same, return 0
    # unless one list is longer, but even that is not an expected local
    # use case
    return cmp(len(x), len(y))

# lame, but it matches at least up to 6 ORDER BY columns
__EXPR = re.compile(r"ORDER BY\s+((\w+\.)?(?P<column_1>\w+)(\s+\w+)?)"
                    r"(,\s+(\w+\.)?(?P<column_2>\w+)(\s+\w+)?)?"
                    r"(,\s+(\w+\.)?(?P<column_3>\w+)(\s+\w+)?)?"
                    r"(,\s+(\w+\.)?(?P<column_4>\w+)(\s+\w+)?)?"
                    r"(,\s+(\w+\.)?(?P<column_5>\w+)(\s+\w+)?)?"
                    r"(,\s+(\w+\.)?(?P<column_6>\w+)(\s+\w+)?)?")

# This appears to be a weak knock-off of FastSerializer.NullCheck
# TODO: There's probably a way to use the actual FastSerializer.NullCheck
__NULL = {FastSerializer.VOLTTYPE_TINYINT: FastSerializer.NULL_TINYINT_INDICATOR,
          FastSerializer.VOLTTYPE_SMALLINT: FastSerializer.NULL_SMALLINT_INDICATOR,
          FastSerializer.VOLTTYPE_INTEGER: FastSerializer.NULL_INTEGER_INDICATOR,
          FastSerializer.VOLTTYPE_BIGINT: FastSerializer.NULL_BIGINT_INDICATOR,
          FastSerializer.VOLTTYPE_FLOAT: FastSerializer.NULL_FLOAT_INDICATOR,
          FastSerializer.VOLTTYPE_STRING: FastSerializer.NULL_STRING_INDICATOR}

SIGNIFICANT_DIGITS = 12

def normalize_value(v, vtype):
    global __NULL
    if v is None:
        return None
    if vtype in __NULL and v == __NULL[vtype]:
        return None
    elif vtype == FastSerializer.VOLTTYPE_FLOAT:
        # round to the desired number of decimal places -- accounting for significant digits before the decimal
        decimal_places = SIGNIFICANT_DIGITS
        abs_v = abs(float(v))
        if abs_v >= 1.0:
            # round to the total number of significant digits, including the integer part
            decimal_places = SIGNIFICANT_DIGITS - 1 - int(math.floor(math.log10(abs_v)))
        # print "DEBUG normalized float:(", round(v, decimal_places), ")"
        return round(v, decimal_places)
    elif vtype == FastSerializer.VOLTTYPE_DECIMAL:
        # print "DEBUG normalized_to:(", decimal.Decimal(v)._rescale(-12, "ROUND_HALF_EVEN"), ")"
        return decimal.Decimal(v)._rescale(-12, "ROUND_HALF_EVEN")
    else:
        # print "DEBUG normalized pass-through:(", v, ")"
        return v

def normalize_values(tuples, columns):
    # 'c' here is a voltdbclient.VoltColumn and
    # I assume t is a voltdbclient.VoltTable.
    if hasattr(tuples, "__iter__"):
        for i in xrange(len(tuples)):
            if hasattr(tuples[i], "__iter__"):
                normalize_values(tuples[i], columns)
            else:
                tuples[i] = normalize_value(tuples[i], columns[i].type)

def project_sorted(row, sorted_cols):
    """Extract the values in the ORDER BY columns from a row.
    """
    return [ row[col] for col in sorted_cols ]

def project_unsorted(row, sorted_cols):
    """Extract the values in the non-ORDERBY columns from a row.
    """
    return [ row[col] for col in xrange(len(row)) if col not in sorted_cols ]

def move_rows(rows, start, end, moveto):
    """Given a list of rows, moves the rows beginning with index 'start'
       (inclusive) and ending with index 'end' (exclusive) to a position
       just before index 'moveto', and returns the result.
    """
    # TODO: temp debug!
    print '      In move_rows...'
    print '        start, end, moveto, len(rows):', start, end, moveto, len(rows)
    print '        rows0:', rows
    if start < 0 or end < 0 or start > len(rows) or end > len(rows) or start > end:
        raise ValueError('Illegal value of start (%d) or end (%d): negative, greater than len(rows) (%d), or start > end' % (start, end, len(rows)))
    elif moveto < 0 or (moveto > start and moveto < end) or moveto > len(rows):
        raise ValueError('Illegal value of moveto (%d): negative, greater than len(rows) (%d), or between start (%d) and end (%d)' % (moveto, len(rows), start, end))
    elif moveto == start or moveto == end:
        return

    newrows = []
    # TODO: temp debug!
    #print '        newrows0 :', newrows
    #print '        rows[0:min(moveto,start)]   :', rows[0:min(moveto,start)]
    newrows.extend(rows[0:min(moveto,start)])
    # TODO: temp debug!
    #print '        newrows1 :', newrows
    if moveto < start:
        # TODO: temp debug!
        #print '        rows[start:end]   :', rows[start:end]
        newrows.extend(rows[start:end])
        # TODO: temp debug!
        #print '        newrows2a:', newrows
        #print '        rows[moveto:start]:', rows[moveto:start]
        newrows.extend(rows[moveto:start])
        # TODO: temp debug!
        #print '        newrows3a:', newrows
    elif moveto > end:
        # TODO: temp debug!
        #print '        rows[end:moveto]  :', rows[end:moveto]
        newrows.extend(rows[end:moveto])
        # TODO: temp debug!
        #print '        newrows2b:', newrows
        #print '        rows[start:end]   :', rows[start:end]
        newrows.extend(rows[start:end])
        # TODO: temp debug!
        #print '        newrows3b:', newrows
    # TODO: temp debug!
    #print '        rows[max(moveto,end):len(rows)]:', rows[max(moveto,end):len(rows)]
    newrows.extend(rows[max(moveto,end):len(rows)])
    # TODO: temp debug!
    print '        newrows4 :', newrows
    #return newrows
    rows[0:len(newrows)] = newrows[0:len(newrows)]

class SortNulls:
    never = 0  # do not re-sort SQL NULL (Python None) values
    first = 1  # always sort SQL NULL (Python None) values first
    last  = 2  # always sort SQL NULL (Python None) values last
    lowest  = 3  # sort SQL NULL (Python None) values first with ORDER BY ASC, last with ORDER BY DESC
    highest = 4  # sort SQL NULL (Python None) values last with ORDER BY ASC, first with ORDER BY DESC

def sort(rows, sorted_cols, desc, sort_nulls=SortNulls.never):
    """Three steps:
        1. Find the subset of rows which have the same values in the ORDER BY
        columns.
        2. Sort them on the rest of the columns.
        3. Optionally, re-sort SQL NULL (Python None) values in the ORDER BY
        columns to be first or last (or first when ASC, last when DESC, or
        vice versa), as specified.
    """
    # TODO: temp debug!
    print '  In sort...'
    print '    sorted_cols:', sorted_cols
    print '    desc       :', desc
    print '    sort_nulls :', sort_nulls
    print '    rows0 (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
    if not sorted_cols:
        rows.sort(cmp=safecmp)
        return

    begin = 0
    prev = None
    unsorteds = lambda row: project_unsorted(row, sorted_cols)

    for i in xrange(len(rows)):
        tmp = project_sorted(rows[i], sorted_cols)
        if prev != tmp:
            if prev:
                # Sort a complete "group", with matching ORDER BY column values
                rows[begin:i] = sorted(rows[begin:i], cmp=safecmp, key=unsorteds)
            prev = tmp
            begin = i

    # Sort the final "group" (of rows with matching ORDER BY column values)
    rows[begin:] = sorted(rows[begin:], cmp=safecmp, key=unsorteds)
    # TODO: temp debug!
    print '    rows1 (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)

    # Sort SQL NULL (Python None) values, in ORDER BY columns, in the
    # specified order (if any)
    if sort_nulls:
        for i in xrange(len(sorted_cols)):
            begin = 0
            prev = None
            for j in xrange(len(rows)+1):
                tmp = []
                if j < len(rows):
                    tmp = project_sorted(rows[j], sorted_cols[0:i+1])
                if prev != tmp:
                    # TODO: temp debug!
                    print '      i, j, prev, tmp:', i, j, prev, tmp
                    if prev and prev[i] is None:
                        # TODO: temp debug!
                        print '        prev and prev[i] is None!'
                        if (   sort_nulls == SortNulls.first or
                              (sort_nulls == SortNulls.highest and desc[i]) or
                              (sort_nulls == SortNulls.lowest  and not desc[i])):
                            # TODO: temp debug!
                            print '        Sort nulls first!'
                            if i == 0:  # first ORDER BY column, so don't need to check others
                                # TODO: temp debug!
                                print '          Will move rows (begin, j, 0):', begin, j, 0
                                print '    rows2a (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                move_rows(rows, begin, j, 0)
                                # TODO: temp debug!
                                print '    rows3a (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                break
                            ref = project_sorted(rows[begin], sorted_cols[0:i])
                            # TODO: temp debug!
                            print '      ref(first):', ref
                            for k in range(begin-1, -2, -1):
                                # TODO: temp debug!
                                maxk = max(k, 0)
                                print '        i, j, k:', i, j, k
                                print '        project_sorted(rows[k], sorted_cols[0:i]):\n        ', project_sorted(rows[maxk], sorted_cols[0:i])
                                if (k < 0 or ref != project_sorted(rows[maxk], sorted_cols[0:i])):
                                    # TODO: temp debug!
                                    print '          Will move rows (begin, j, k+1):', begin, j, k+1
                                    print '    rows2b (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                    #rows = move_rows(rows, begin, j, k+1)
                                    move_rows(rows, begin, j, k+1)
                                    # TODO: temp debug!
                                    print '    rows3b (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                    break
                        elif ( sort_nulls == SortNulls.last or
                              (sort_nulls == SortNulls.lowest  and desc[i]) or
                              (sort_nulls == SortNulls.highest and not desc[i])):
                            # TODO: temp debug!
                            print '        Sort nulls last!'
                            if i == 0:  # first ORDER BY column, so don't need to check others
                                # TODO: temp debug!
                                print '          Will move rows (begin, j, len(rows)):', begin, j, len(rows)
                                print '    rows2c (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                move_rows(rows, begin, j, len(rows))
                                # TODO: temp debug!
                                print '    rows3c (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                break
                            ref = project_sorted(rows[begin], sorted_cols[0:i])
                            # TODO: temp debug!
                            print '        rows[begin]:', rows[begin]
                            print '        sorted_cols[0:i]:', sorted_cols[0:i]
                            print '        ref(last):', ref
                            for k in range(j, len(rows)+1):
                                # TODO: temp debug!
                                mink = min(k, len(rows)-1)
                                print '        k, rows[k]:', k, rows[mink]
                                print '        project_sorted:', project_sorted(rows[mink], sorted_cols[0:i])
                                if (k >= len(rows) or ref != project_sorted(rows[k], sorted_cols[0:i])):
                                    # TODO: temp debug!
                                    print '          Will move rows (begin, j, k):', begin, j, k
                                    print '    rows2d (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                    #rows = move_rows(rows, begin, j, k)
                                    move_rows(rows, begin, j, k)
                                    # TODO: temp debug!
                                    print '    rows3d (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)
                                    break
                    prev = tmp
                    begin = j
    # TODO: temp debug!
    print '    rows4 (' + str(len(rows)) + '):\n     ', '\n      '.join(str(r) for r in rows)

def parse_sql(x):
    """Finds if the SQL statement contains an ORDER BY command, and returns
       the names of the ORDER BY columns.
    """

    global __EXPR

    result = __EXPR.search(x)
    if result:
        # returns the ORDER BY column names, in the order used in that clause
        getcol = lambda i: result.groupdict()['column_'+str(i)]
        return map(lambda i: getcol(i), filter(lambda i: getcol(i), range(1,7)) )
    else:
        return None

def parse_for_order_by_desc(sql, col_name):
    """Checks whether the SQL statement contains an ORDER BY clause for the
       specified column name, followed by DESC (meaning descending order);
       returns True if so, otherwise False.
    """
    desc_expr = re.compile(r"ORDER BY([\s\w.]*,)*\s+(\w+\.)?" + col_name.upper() + "\sDESC")
    result = desc_expr.search(sql)
    if result:
        return True
    else:
        return False

def normalize(table, sql, sort_nulls=SortNulls.never):
    """Normalizes the result tuples of ORDER BY statements.
    """
    normalize_values(table.tuples, table.columns)

    sql_upper = sql.upper()
    sort_cols = parse_sql(sql_upper)
    # TODO: temp debug!
    print '\nIn normalize...'
    print '  table.columns.name:', map(lambda i: table.columns[i].name, range(len(table.columns)))
    print '  sort_cols:', sort_cols
    indices = []
    desc = []
    if sort_cols:
        # gets the ORDER BY column indices, in the order used in that clause
        for i in xrange(len(sort_cols)):                
            for j in xrange(len(table.columns)):
                if sort_cols[i] == table.columns[j].name:
                    indices.append(j)
                    desc.append(parse_for_order_by_desc(sql_upper, sort_cols[i]))
                    break
    # TODO: temp debug!
    print '  indices:', indices
    print '  desc   :', desc

    # Make sure if there is an ORDER BY clause, the order by columns appear in
    # the result table. Otherwise all the columns will be sorted by the
    # normalizer.
    # TODO: temp debug!
    print 'table.tuples0 (' + str(len(table.tuples)) + '):\n     ', '\n      '.join(str(r) for r in table.tuples)
    sort(table.tuples, indices, desc, sort_nulls)
    # TODO: temp debug!
    print 'table.tuples1 (' + str(len(table.tuples)) + '):\n     ', '\n      '.join(str(r) for r in table.tuples)

    return table

def compare_results(suite, seed, statements_path, hsql_path, jni_path, output_dir, report_all, extra_stats):
    return generate_html_reports(suite, seed, statements_path, hsql_path,
            jni_path, output_dir, report_all, extra_stats)
