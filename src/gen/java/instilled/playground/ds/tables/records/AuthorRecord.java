/**
 * This class is generated by jOOQ
 */
package instilled.playground.ds.tables.records;


import instilled.playground.ds.tables.Author;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.0"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthorRecord extends UpdatableRecordImpl<AuthorRecord> implements Record3<Integer, String, String> {

	private static final long serialVersionUID = 778739976;

	/**
	 * Setter for <code>sandbox.author.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>sandbox.author.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>sandbox.author.first_name</code>.
	 */
	public void setFirstName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>sandbox.author.first_name</code>.
	 */
	public String getFirstName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>sandbox.author.last_name</code>.
	 */
	public void setLastName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>sandbox.author.last_name</code>.
	 */
	public String getLastName() {
		return (String) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, String> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, String> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Author.AUTHOR.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Author.AUTHOR.FIRST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Author.AUTHOR.LAST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthorRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthorRecord value2(String value) {
		setFirstName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthorRecord value3(String value) {
		setLastName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthorRecord values(Integer value1, String value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AuthorRecord
	 */
	public AuthorRecord() {
		super(Author.AUTHOR);
	}

	/**
	 * Create a detached, initialised AuthorRecord
	 */
	public AuthorRecord(Integer id, String firstName, String lastName) {
		super(Author.AUTHOR);

		setValue(0, id);
		setValue(1, firstName);
		setValue(2, lastName);
	}
}
