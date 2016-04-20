package com.jenkov.db.itf;

import com.jenkov.db.itf.mapping.IObjectMapping;
import com.jenkov.db.itf.mapping.IKeyValue;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.List;
import java.util.Collection;

/**
 * This interface represents all the functions made available by the object reader of Butterfly Persistence.
 * The object reader is responsible for all reading of records and turning them into objects with
 * the values read inside.
 * @author Jakob Jenkov,  Jenkov Development
 */
public interface IObjectReader {


    /**
     * Sets the database that this IObjectReader is supposed to read objects from. The Database
     * object contains information about the features the database supports.
     *
     * @param database The database to set on this object reader.
     */
    public void setDatabase(Database database);


    /**
     * Reads an object from the database using an object mapping, the primary key, an SQL string (explained below)
     * and a database connection. Use this method only with single column primary keys and single object
     * primary key values.
     *
     * <br/><br/>
     * The SQL string has to be of the format
     * <code>select [field1], [field2], [field3] (etc.) from [table] where [fieldPrimaryKey] = ?</code>. The
     * primary key value must be a ? for use with a PreparedStatement. The object reader will insert the
     * value in a PreparedStatement internally. The SQLGenerator class can generate a suitable SQL string for
     * use with the object reader, so you don't have to do it yourself.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> yourself when you are done with it.
     * @param mapping The object mapping to use to read this object.
     * @param primaryKey The primary key value of the object to read from the database.
     * @param sql The SQL generated by the SQLGenerator class (or by you if you prefer).
     * @param connection The database connection to use for reading the object. Remember that
     * you must close this connection yourself afterwards. The object reader only closes objects
     * it opens itself.
     * @return A <code>List</code> containing the objects read by the given primary keys. If
     *         no records were found matching the given primary keys the list returned will be empty.
     *         An empty list will also be returned if the list of primary keys is empty.
     * 
     * @throws PersistenceException If something goes wrong during the read.
     */
    public  Object readByPrimaryKey(IObjectMapping mapping, Object primaryKey, String sql, Connection connection) throws PersistenceException;


    /**
     * Reads an object from the database using an object mapping, the primary key, an SQL string (explained below)
     * and a database connection. This method can be used with both single column primary key tables
     * and compound primary key tables.
     *
     * <br/><br/>
     * The SQL string has to be of the format
     * <code>select [field1], [field2], [field3] (etc.) from [table]
     * where [fieldPrimaryKey1] = ? and [fieldPrimaryKey2] = ? (etc.)</code>. The
     * primary key value must be a ? for use with a PreparedStatement. The object reader will insert the
     * value in a PreparedStatement internally. The SQLGenerator class can generate a suitable SQL string for
     * use with the object reader, so you don't have to do it yourself.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> yourself when you are done with it.
     * @param mapping The object mapping to use to read this object.
     * @param primaryKey The primary key value of the object to read from the database.
     * @param sql The SQL generated by the SQLGenerator class (or by you if you prefer).
     * @param connection The database connection to use for reading the object. Remember that
     * you must close this connection yourself afterwards. The object reader only closes objects
     * it opens itself.
     * @return A <code>List</code> containing the objects read by the given primary keys. If
     *         no records were found matching the given primary keys the list returned will be empty.
     *         An empty list will also be returned if the list of primary keys is empty.
     *
     * @throws PersistenceException If something goes wrong during the read.
     */
    public  Object readByPrimaryKey(IObjectMapping mapping, IKeyValue primaryKey, String sql, Connection connection) throws PersistenceException;


    /**
     * Reads an object from the given <code>ResultSet</code> using the given object mapping.
     * The <code>ResultSet</code> has to be positioned at the record to read the object from. Not before.
     * If the <code>ResultSet</code> has just been opened, you will have to call the <code>ResultSet.next()</code>
     * or the <code>ResultSet.first()</code> before calling this method.
     *
     * <br/><br/>
     * You must close the <code>ResultSet</code>
     * yourself when you are done with it.
     * @param mapping The object mapping to use to read the object.
     * @param result The <code>ResultSet</code> to read the object from. Remember to close this yourself when you are
     *        done with it.
     * @return The object read using the object mapping and the <code>ResultSet</code>
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public Object read(IObjectMapping mapping, ResultSet result)                    throws PersistenceException;


    /**
     * Reads an object using the provided object mapping, SQL and <code>Statement</code> instance. The SQL can be
     * written freely as long as it is valid, and your database understands it.
     * If the <code>ResultSet</code> returned by the <code>Statement</code>
     * instance returns more than one record, the first one is used to read the object.
     * If the <code>ResultSet</code> is empty null is returned.
     *
     * <br/><br/>
     * Remember to close the <code>Statement</code> object yourself when you are done with it.
     * @param mapping The object mapping to use to read the object.
     * @param statement The <code>Statement</code> instance to use to execute the SQL.
     * @param sql The SQL that locates the record to use to read as an object.
     * @return The object read from executing the SQL and reading the first object of the <code>ResultSet</code>.
     *          Null if the <code>ResultSet</code> is empty.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public Object read(IObjectMapping mapping, Statement statement, String sql)     throws PersistenceException;


    /**
     * Reads an object using the given object mappping, SQL, and the <code>Connection</code> instance.
     * The SQL can be freely written as long as it is valid and your database understands it.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance yourself when you are done with it.
     * @param mapping The object mapping to use to read the object.
     * @param sql The SQL that locates the record in the database to read the object from.
     * @param connection The database connection to use to read this object.
     *          Remember to close the <code>Connection</code> instance yourself when you are done with it.
     * @return The object read using the SQL, object mapping and the database connection.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public Object read(IObjectMapping mapping, String sql, Connection connection)   throws PersistenceException;


    /**
     * Reads an object from the database using the given object mapping and <code>PreparedStatement</code>.
     * You must have set all values on the <code>PreparedStatement</code> instance before calling this method
     * (using the appropriate <code>PreparedStatement.setXXX(int index, XXX value) methods ).
     *
     * <br/><br/>
     * Remember to close the <code>PreparedStatement</code> instance yourself when you are done with it.
     * @param mapping The object mapping to use to read this object.
     * @param preparedStatement The <code>PreparedStatement</code> that locates the record that this
     *        object is to be read from.
     * @return The object read from the database using the given object mapping and <code>PreparedStatement</code>
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public Object read(IObjectMapping mapping, PreparedStatement preparedStatement) throws PersistenceException;


    /**
     * Reads an object from the database using the given object mapping, SQL string, and parameters.
     *
     * <br/><br/>
     * A <code>PreparedStatement</code> instance will be created using the
     * given SQL string, and the parameters collection will be inserted into it.
     * Therefore the SQL string should have the same format as those used with a
     * <code>PreparedStatement</code>. The parameters will be inserted in the
     * sequence returned by the parameter collection's iterator.
     *
     * <br/><br/>
     * If the <code>ResultSet</code> generated
     * by the <code>PreparedStatement</code> instance contains
     * more than one record, only the first record in the
     * <code>ResultSet</code> will be read into an object and returned.
     *
     * @param mapping     The object mapping to use to read this object.
     * @param sql         The SQL string to use for the <code>PreparedStatement</code>.
     * @param parameters  The parameters to insert into the <code>PreparedStatement</code>.
     * @param connection  The database connection to use to create the <code>PreparedStatement</code> instance.
     * @return The object read from the database.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public Object read(IObjectMapping mapping, String sql, Collection parameters, Connection connection)
            throws PersistenceException;


    /**
     * Reads an object from the database using the given object mapping, SQL string, and parameters.
     *
     * <br/><br/>
     * A <code>PreparedStatement</code> instance will be created using the
     * given SQL string, and the parameters in the array will be inserted into it.
     * Therefore the SQL string should have the same format as those used with a
     * <code>PreparedStatement</code>. The parameters will be inserted in the
     * sequence of their indexes in the array, meaning parameters[0] will be inserted first.
     *
     * <br/><br/>
     * If the <code>ResultSet</code> generated
     * by the <code>PreparedStatement</code> instance contains
     * more than one record, only the first record in the
     * <code>ResultSet</code> will be read into an object and returned.
     *
     * @param mapping     The object mapping to use to read this object.
     * @param sql         The SQL string to use for the <code>PreparedStatement</code>.
     * @param parameters  The parameters to insert into the <code>PreparedStatement</code>.
     * @param connection  The database connection to use to create the <code>PreparedStatement</code> instance.
     * @return The object read from the database.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public Object read(IObjectMapping mapping, String sql, Object[] parameters, Connection connection)
            throws PersistenceException;


    /**
     * Reads a list of objects from the database using an object mapping, the primary keys of the objects to read,
     * an SQL string (explained below)
     * and a database connection. The SQL string has to be of the format
     * <code>select [field1], [field2], [field3] ... from [table] where [fieldPrimaryKey] in (?, ?, ? ...)</code>.
     * The primary key values must be ?-marks for use with a PreparedStatement. The object reader will insert the
     * values in a PreparedStatement internally. The SQLGenerator class can generate a suitable SQL string for
     * use with the object reader, so you don't have to do it yourself.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> yourself when you are done with it.
     * @param mapping The object mapping to use to read the objects.
     * @param primaryKeys The primary key values of the objects to read from the database.
     * @param sql The SQL generated by the SQLGenerator class (or by you if you prefer).
     * @param connection The database connection to use for reading the object. Remember that
     * you must close this connection yourself afterwards. The object reader only closes objects
     * it opens itself.
     * @return The objects read by the given primary key, or null if no matching record was found in the database.
     * @throws PersistenceException If something goes wrong during the read.
     */
    public  List readListByPrimaryKeys(IObjectMapping mapping, Collection primaryKeys, String sql, Connection connection) throws PersistenceException;


    /**
     * Reads a list of objects from the database using an object mapping, the primary keys of the objects to read,
     * an SQL string (explained below) and a database connection. The list of objects can be filtered by passing
     * an <code>IReadFilter</code> instance to this method. If the filter parameter is null, no filtering will
     * be done.
     *
     *
     * <br/><br/>
     * The SQL string has to be of the format
     * <code>select [field1], [field2], [field3] ... from [table] where [fieldPrimaryKey] in (?, ?, ? ...)</code>.
     * The primary key values must be ?-marks for use with a PreparedStatement. The object reader will insert the
     * values in a PreparedStatement internally. The SQLGenerator class can generate a suitable SQL string for
     * use with the object reader, so you don't have to do it yourself.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> yourself when you are done with it.
     * @param mapping The object mapping to use to read the objects.
     * @param primaryKeys The primary key values of the objects to read from the database.
     * @param sql The SQL generated by the SQLGenerator class (or by you if you prefer).
     * @param connection The database connection to use for reading the object. Remember that
     * you must close this connection yourself afterwards. The object reader only closes objects
     * it opens itself.
     * 
     * @return The objects read by the given primary key, or null if no matching record was found in the database.
     * @throws PersistenceException If something goes wrong during the read.
     */
    public  List readListByPrimaryKeys(IObjectMapping mapping, Collection primaryKeys, String sql,
                                       Connection connection, IReadFilter filter) throws PersistenceException;


    /**
     * Reads a list of objects from the given <code>ResultSet</code> using the object mapping.
     * The <code>ResultSet</code> must be positioned at the first record to be read. Not before.
     * All records in the <code>ResultSet</code> will be read as objects according to the given
     * object mapping. The list returned will contain the objects read in the same sequence as
     * the coresponding records appear in the <code>ResultSet</code>.
     *
     * <br/><br/>
     * Remember to close the <code>ResultSet</code> yourself when you are done with it.
     * @param mapping The object mapping to use to read the objects.
     * @param result The <code>ResultSet</code> to read the objects from.
     * @return A list of objects read from the <code>ResultSet</code> in the same sequence as the
     *         coresponding records appear in the <code>ResultSet</code>
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, ResultSet result)                     throws PersistenceException;


    /**
     * Reads a list of objects from the given <code>ResultSet</code> using the given object mapping
     * and read filter. The <code>ResultSet</code> must point to the first record to be read. Not before.
     * The read filter can either accept or reject records in the
     * <code>ResultSet</code>, and can also signal that it won't accept anymore records at all,
     * so the object reader can stop the <code>ResultSet</code> iteration.
     *
     * The object will appear in the returned list in the same sequence they are encountered in the
     * <code>ResultSet</code>.
     *
     * @param mapping The object mapping to use to read the objects.
     * @param result The <code>ResultSet</code> to read the objects from.
     * @param filter The filter to apply to the <code>ResultSet</code>.
     *              Passing null in this parameter will result in no filtering.
     * @return A list of objects read from the <code>ResultSet</code> according to the object mapping.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, ResultSet result, IReadFilter filter) throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping, <code>Statement</code> and
     * SQL string. The objects will appear in the list in the same sequence as they appear in the
     * <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * Remember to close the <code>Statement</code> instance when you are done with it. This method will not
     * close it.
     * @param mapping The object mapping to use to read the objects.
     * @param statement The <code>Statement</code> instance to use to execute the SQL string.
     * @param sql The SQL string locating the records in the database to read into objects.
     * @return A list of objects read from the <code>ResultSet</code> generated on the base of the SQL string.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, Statement statement, String sql)                      throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping, <code>Statement</code> and
     * SQL string. The objects will appear in the list in the same sequence as they appear in the
     * <code>ResultSet</code> generated by the SQL string.
     * The read filter can either accept or reject records in the
     * <code>ResultSet</code>, and can also signal that it won't accept anymore records at all,
     * so the object reader can stop the <code>ResultSet</code> iteration.

     * <br/><br/>
     * Remember to close the <code>Statement</code> instance when you are done with it. This method will not
     * close it.
     * @param mapping The object mapping to use to read the objects.
     * @param statement The <code>Statement</code> instance to use to execute the SQL string.
     * @param sql The SQL string locating the records in the database to read into objects.
     * @param filter The filter to apply to the <code>ResultSet</code> generated from the SQL string.
     *              Passing null in this parameter will result in no filtering.
     * @return A list of objects read from the database.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, Statement statement, String sql, IReadFilter filter)  throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping, SQL string,
     * and <code>Connection</code>. The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     * @param mapping The object mapping to use to read the objects.
     * @param sql The SQL string locating the records in the database to read into objects.
     * @param connection The database connection to use to execute the SQL via.
     * @return A list of objects read from the <code>ResultSet</code> generated by executing the
     *          SQL string.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, String sql, Connection connection)                     throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping, SQL string,
     * and <code>Connection</code>. The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     * The read filter can either accept or reject records in the
     * <code>ResultSet</code>, and can also signal that it won't accept anymore records at all,
     * so the object reader can stop the <code>ResultSet</code> iteration.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     * @param mapping The object mapping to use to read the objects.
     * @param sql The SQL string locating the records in the database to read into objects.
     * @param connection The database connection to use to execute the SQL via.
     * @param filter The filter to apply to the <code>ResultSet</code> generated from the SQL string.
     *              Passing null in this parameter will result in no filtering.
     * @return A list of objects read from the <code>ResultSet</code> generated by executing the
     *          SQL string.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, String sql, Connection connection, IReadFilter filter) throws PersistenceException;



    /**
     * Reads a list of objects from the database using the given object mapping
     * and <code>PreparedStatement</code>.
     * You must have set all values on the <code>PreparedStatement</code> instance before calling this method
     * (using the appropriate <code>PreparedStatement.setXXX(int index, XXX value) methods ).
     *
     * The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     * @param mapping The object mapping to use to read the objects.
     * @param preparedStatement The <code>PreparedStatement</code> that locates the record that this
     *        object is to be read from.
     * @return A list of objects read from the <code>ResultSet</code> generated by executing the
     *          <code>PreparedStatement</code>.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, PreparedStatement preparedStatement)                     throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping, SQL string, and parameters.
     *
     * <br/><br/>
     * A <code>PreparedStatement</code> instance will be created using the
     * given SQL string, and the parameters collection will be inserted into it.
     * Therefore the SQL string should have the same format as those used with a
     * <code>PreparedStatement</code>. The parameters will be inserted in the
     * sequence returned by the parameter collection's iterator.
     *
     * <br/><br/>
     * The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     *
     * @param mapping     The object mapping to use to read the list of objects.
     * @param sql         The SQL string to use for the <code>PreparedStatement</code>.
     * @param parameters  The parameters to insert into the <code>PreparedStatement</code>.
     * @param connection  The database connection to use to create the <code>PreparedStatement</code> instance.
     * @return The objects read from the database.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, String sql, Collection parameters, Connection connection)
    throws PersistenceException;

    
    /**
     * Reads a list of objects from the database using the given object mapping, SQL string, and parameters.
     *
     * <br/><br/>
     * A <code>PreparedStatement</code> instance will be created using the
     * given SQL string, and the parameters in the array will be inserted into it.
     * Therefore the SQL string should have the same format as those used with a
     * <code>PreparedStatement</code>. The parameters will be inserted in the
     * sequence of their indexes in the array, meaning parameters[0] will be inserted first.
     *
     * <br/><br/>
     * The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     *
     * @param mapping     The object mapping to use to read the list of objects.
     * @param sql         The SQL string to use for the <code>PreparedStatement</code>.
     * @param parameters  The parameters to insert into the <code>PreparedStatement</code>.
     * @param connection  The database connection to use to create the <code>PreparedStatement</code> instance.
     * @return The objects read from the database.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, String sql, Object[] parameters, Connection connection)
    throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping
     * and <code>PreparedStatement</code>.
     * You must have set all values on the <code>PreparedStatement</code> instance before calling this method
     * (using the appropriate <code>PreparedStatement.setXXX(int index, XXX value) methods ).
     *
     * <br/><br/>
     * The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * The read filter can either accept or reject records in the
     * <code>ResultSet</code>, and can also signal that it won't accept anymore records at all,
     * so the object reader can stop the <code>ResultSet</code> iteration.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     *
     * @param mapping The object mapping to use to read the objects.
     * @param preparedStatement The <code>PreparedStatement</code> that locates the record that this
     *        object is to be read from.
     * @param filter The filter to apply to the <code>ResultSet</code> generated from the SQL string.
     *              Passing null in this parameter will result in no filtering.
     * @return A list of objects read from the <code>ResultSet</code> generated by executing the
     *          <code>PreparedStatement</code>.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, PreparedStatement preparedStatement, IReadFilter filter) throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping, SQL string, and parameters.
     *
     * <br/><br/>
     * A <code>PreparedStatement</code> instance will be created using the
     * given SQL string, and the parameters collection will be inserted into it.
     * Therefore the SQL string should have the same format as those used with a
     * <code>PreparedStatement</code>. The parameters will be inserted in the
     * sequence returned by the parameter collection's iterator.
     *
     * <br/><br/>
     * The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * The read filter can either accept or reject records in the
     * <code>ResultSet</code>, and can also signal that it won't accept anymore records at all,
     * so the object reader can stop the <code>ResultSet</code> iteration.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     *
     * @param mapping     The object mapping to use to read the list of objects.
     * @param sql         The SQL string to use for the <code>PreparedStatement</code>.
     * @param parameters  The parameters to insert into the <code>PreparedStatement</code>.
     * @param connection  The database connection to use to create the <code>PreparedStatement</code> instance.
     * @return The objects read from the database.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, String sql, Collection parameters, Connection connection, IReadFilter filter)
    throws PersistenceException;


    /**
     * Reads a list of objects from the database using the given object mapping, SQL string, and parameters.
     *
     * <br/><br/>
     * A <code>PreparedStatement</code> instance will be created using the
     * given SQL string, and the parameters in the array will be inserted into it.
     * Therefore the SQL string should have the same format as those used with a
     * <code>PreparedStatement</code>. The parameters will be inserted in the
     * sequence of their indexes in the array, meaning parameters[0] will be inserted first.
     *
     * <br/><br/>
     * The objects will appear in the list in the same sequence
     * as they appear in the <code>ResultSet</code> generated by the SQL string.
     *
     * <br/><br/>
     * The read filter can either accept or reject records in the
     * <code>ResultSet</code>, and can also signal that it won't accept anymore records at all,
     * so the object reader can stop the <code>ResultSet</code> iteration.
     *
     * <br/><br/>
     * Remember to close the <code>Connection</code> instance when you are done with it. This method will not
     * close it.
     *
     * @param mapping     The object mapping to use to read the list of objects.
     * @param sql         The SQL string to use for the <code>PreparedStatement</code>.
     * @param parameters  The parameters to insert into the <code>PreparedStatement</code>.
     * @param connection  The database connection to use to create the <code>PreparedStatement</code> instance.
     * @return The objects read from the database.
     * @throws PersistenceException If anything goes wrong during the read.
     */
    public List readList(IObjectMapping mapping, String sql, Object[] parameters, Connection connection, IReadFilter filter)
    throws PersistenceException;


}