package ortherproject;

/**
 * Title:        网站内容管理（TRSWCM）
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      www.trs.com.cn
 * @author EPROBITI
 * @version 1.0
 *
 * Created:         2001.8
 * Last Modified:   2001.9.21/2002.02.28
 * Description:
 *      class CDBText —— 数据库文本对象的定义和实现
 */

/**
 * <p>
 * Title: TRS 内容协作平台（TRS WCM）
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * class CDBText —— 数据库文本对象的定义和实现
 * </p>
 * <p>
 * Copyright: Copyright (c) 2001-2002 TRS信息技术有限公司
 * </p>
 * <p>
 * Company: TRS信息技术有限公司(www.trs.com.cn)
 * </p>
 * 
 * @author TRS信息技术有限公司
 * @version 1.0
 */

//===================================================
//  CDBText: 数据库文对象，用以处理数据库中文本型字段
//
//  说明：区分 Orcale Db 和 SQL Server 数据库
//===================================================
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;

public class CDBText implements Serializable {
    public final static boolean JDBC_IS2 = false; // JDBC 版本是否为2.0

    private String m_sText = null;

    private transient DBType m_oDBType;

    // 私有数据
    private boolean m_bLoaded = false; // 是否装载了数据

    // ======================================================
    // 构造函数
    /**
     * 构造函数
     * 
     * @param _nDBType
     *            数据库类型（例如DBTypes.ORACLE等）
     */
    public CDBText(int _nDBType) {
        this.setDBType(DBTypes.getDBType(_nDBType));
    }

    /**
     * 构造函数
     * 
     * @param _nDBType
     *            数据库类型（例如DBTypes.ORACLE等）
     * @param _sText
     *            大文本数据
     */
    public CDBText(int _nDBType, String _sText) {
        this.setDBType(DBTypes.getDBType(_nDBType));
        this.setText(_sText);
    }

    public CDBText(DBType _dBType, String _sText) {
        this.setDBType(_dBType);
        this.setText(_sText);
    }

    public CDBText(DBType _dBType) {
        this.setDBType(_dBType);
    }

    // =======================================================
    // 属性存取

    // DBType：数据库类型

    /**
     * 获取数据库类型对象
     * 
     * @return 数据库类型对象
     */
    public DBType getDBType() {
        return m_oDBType;
    }

    // Loaded: 是否装载了数据
    /**
     * 判断是否装载了数据
     * 
     * @return
     */
    public boolean isLoaded() {
        return m_bLoaded;
    }

    // Text：文本内容
    /**
     * 获取文本内容
     * 
     * @return
     */
    public String getText() {
        return m_sText;
    }

    /**
     * 设置文本内容
     * 
     * @param p_sText
     */
    public void setText(String p_sText) {
        m_sText = p_sText;
        m_bLoaded = true; // 置装载标志
    }

    /**
     * 判断文本内容是否为空
     * 
     * @return
     */
    public boolean isNull() {
        return (m_sText == null);
    }

    /**
     * 取文本长度
     * 
     * @return
     */
    public int getLength() {
        return (m_sText == null ? 0 : m_sText.length());
    }

    /**
     * 字符串输出
     * 
     * @return
     */
    public String toString() {
        // -- 防止out.println调用时错误的输出null，因此先判断如果内容为null则输出"" fx@2003.12.19
        return (this.getText() == null ? "" : this.getText());
    }

    // ==================================================
    // 数据库读/写操作

    /**
     * 从数据库中读取Clob/Text型字段的值
     * 
     * @param p_rsData
     *            记录集
     * @param p_sFieldName
     *            字段名称
     * @throws CMyException
     */
    public void readFromRs(ResultSet p_rsData, String p_sFieldName)
            throws CMyException {
        this.setText(getDBType().getClob(p_rsData, JDBC_IS2, p_sFieldName));
    }// END: readFromRs

    /**
     * 从数据库中读取Clob/Text型字段的值
     * 
     * @param p_rsData
     *            记录集
     * @param p_nFieldIndex
     *            字段索引
     * @throws CMyException
     */
    public void readFromRs(ResultSet p_rsData, int p_nFieldIndex)
            throws CMyException {
        this.setText(getDBType().getClob(p_rsData, JDBC_IS2, p_nFieldIndex));
    }// END: readFromRs

    // =================================================================
    // 将Clob/Text型字段值写入数据库

    /**
     * 将Clob/Text型字段值写入数据库
     * 
     * @param p_oConn
     *            数据库连接对象
     * @param p_sTableName
     *            数据表名
     * @param p_sWhere
     *            where语句
     * @param p_sIdFieldName
     *            ID字段名称
     * @param p_sTextFieldName
     *            大字段名称
     * @return @throws CMyException
     */
    public boolean updateToDB(Connection p_oConn, String p_sTableName,
            String p_sWhere, String p_sIdFieldName, String p_sTextFieldName)
            throws CMyException {
        return getDBType().setClob(p_oConn, p_sTableName, p_sWhere,
                p_sIdFieldName, p_sTextFieldName, this.getText());
    }// END: updateToDb

    /**
     * @param type
     */
    public void setDBType(DBType type) {
        m_oDBType = type;
    }

    /**
     * @see Object#equals(java.lang.Object)
     */
    public boolean equals(Object _another) {
        if (_another == null || !(_another instanceof CDBText)) {
            return false;
        }

        // else
        String sText = ((CDBText) _another).getText();
        if (sText == null) {
            // return sText == null;
            return (m_sText == null);
        }

        // return sText.equals(sText);
        // wenyh@2005-6-2 14:32:42 add comment:modified
        return sText.equals(m_sText);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(m_oDBType.getType());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        this.setDBType(DBTypes.getDBType(in.readInt()));
    }

}