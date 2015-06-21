package org.voltdb.sqlparser.semantics.grammar;

/**
 * This contains one error message.  It has a line, a column
 * a severity and the message text.
 *
 * @author bwhite
 *
 */
public class ErrorMessage {
    public enum Severity {
        Info,
        Warning,
        Error,
        Fatal
    }
    int m_line;
    int m_col;
    String m_msg;
    Severity m_severity;

    public ErrorMessage(int aLine, int aCol, Severity aSeverity, String aMsg) {
        m_line = aLine;
        m_col = aCol;
        m_msg = aMsg;
        m_severity = aSeverity;
    }
    public final int getLine() {
        return m_line;
    }
    public final int getCol() {
        return m_col;
    }
    public final String getMsg() {
        return m_msg;
    }
    public final Severity getSeverity() {
        return m_severity;
    }

}
