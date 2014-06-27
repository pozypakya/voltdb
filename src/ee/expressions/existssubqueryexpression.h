/* This file is part of VoltDB.
 * Copyright (C) 2008-2014 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef HSTOREEXISTSSUBQUERYEXPRESSION_H
#define HSTOREEXISTSSUBQUERYEXPRESSION_H

#include "common/NValue.hpp"
#include "expressions/abstractexpression.h"

namespace voltdb {

class ExistsSubqueryExpression : public AbstractExpression {
    public:

    ExistsSubqueryExpression(AbstractExpression* subqueryExpresion);

    NValue eval(const TableTuple *tuple1, const TableTuple *tuple2) const;

    std::string debugInfo(const std::string &spacer) const;

};

inline NValue ExistsSubqueryExpression::eval(const TableTuple *tuple1, const TableTuple *tuple2) const {
    NValue result = m_left->eval(tuple1, tuple2);
    if (result.isFalse()) {
        // The subquery result set is empty. The EXISTS expression evaluates to FALSE
        return result;
    } else {
        // The subquery result set contains at least a tuple either NULL or not NULL
        // The EXISTS expression evaluates to TRUE
        return NValue::getTrue();
    }
}

}
#endif
