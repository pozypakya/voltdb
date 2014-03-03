/* This file is part of VoltDB.
 * Copyright (C) 2008-2014 VoltDB Inc.
 *
 * This file contains original code and/or modifications of original code.
 * Any modifications made by VoltDB Inc. are licensed under the following
 * terms and conditions:
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

#include "subqueryexpression.h"

#include "common/debuglog.h"

namespace voltdb {

    SubqueryExpression::SubqueryExpression(int subqueryId)
        : AbstractExpression(EXPRESSION_TYPE_SUBQUERY),
        m_subqueryId(subqueryId)
        //, m_subqueryExecutionList()
    {
        VOLT_TRACE("SubqueryExpression %d", subqueryId);
    };

}
