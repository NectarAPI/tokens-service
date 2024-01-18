/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ke.co.nectar.hsm.prism;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@jakarta.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)", date = "2023-03-20")
public class MeterConfigAmendment implements org.apache.thrift.TBase<MeterConfigAmendment, MeterConfigAmendment._Fields>, java.io.Serializable, Cloneable, Comparable<MeterConfigAmendment> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MeterConfigAmendment");

  private static final org.apache.thrift.protocol.TField TO_SGC_FIELD_DESC = new org.apache.thrift.protocol.TField("toSgc", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField TO_KRN_FIELD_DESC = new org.apache.thrift.protocol.TField("toKrn", org.apache.thrift.protocol.TType.I16, (short)2);
  private static final org.apache.thrift.protocol.TField TO_TI_FIELD_DESC = new org.apache.thrift.protocol.TField("toTi", org.apache.thrift.protocol.TType.I16, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MeterConfigAmendmentStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MeterConfigAmendmentTupleSchemeFactory();

  public int toSgc; // required
  public short toKrn; // required
  public short toTi; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TO_SGC((short)1, "toSgc"),
    TO_KRN((short)2, "toKrn"),
    TO_TI((short)3, "toTi");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TO_SGC
          return TO_SGC;
        case 2: // TO_KRN
          return TO_KRN;
        case 3: // TO_TI
          return TO_TI;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TOSGC_ISSET_ID = 0;
  private static final int __TOKRN_ISSET_ID = 1;
  private static final int __TOTI_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TO_SGC, new org.apache.thrift.meta_data.FieldMetaData("toSgc", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TO_KRN, new org.apache.thrift.meta_data.FieldMetaData("toKrn", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.TO_TI, new org.apache.thrift.meta_data.FieldMetaData("toTi", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MeterConfigAmendment.class, metaDataMap);
  }

  public MeterConfigAmendment() {
  }

  public MeterConfigAmendment(
    int toSgc,
    short toKrn,
    short toTi)
  {
    this();
    this.toSgc = toSgc;
    setToSgcIsSet(true);
    this.toKrn = toKrn;
    setToKrnIsSet(true);
    this.toTi = toTi;
    setToTiIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MeterConfigAmendment(MeterConfigAmendment other) {
    __isset_bitfield = other.__isset_bitfield;
    this.toSgc = other.toSgc;
    this.toKrn = other.toKrn;
    this.toTi = other.toTi;
  }

  public MeterConfigAmendment deepCopy() {
    return new MeterConfigAmendment(this);
  }

  @Override
  public void clear() {
    setToSgcIsSet(false);
    this.toSgc = 0;
    setToKrnIsSet(false);
    this.toKrn = 0;
    setToTiIsSet(false);
    this.toTi = 0;
  }

  public int getToSgc() {
    return this.toSgc;
  }

  public MeterConfigAmendment setToSgc(int toSgc) {
    this.toSgc = toSgc;
    setToSgcIsSet(true);
    return this;
  }

  public void unsetToSgc() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOSGC_ISSET_ID);
  }

  /** Returns true if field toSgc is set (has been assigned a value) and false otherwise */
  public boolean isSetToSgc() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOSGC_ISSET_ID);
  }

  public void setToSgcIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOSGC_ISSET_ID, value);
  }

  public short getToKrn() {
    return this.toKrn;
  }

  public MeterConfigAmendment setToKrn(short toKrn) {
    this.toKrn = toKrn;
    setToKrnIsSet(true);
    return this;
  }

  public void unsetToKrn() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOKRN_ISSET_ID);
  }

  /** Returns true if field toKrn is set (has been assigned a value) and false otherwise */
  public boolean isSetToKrn() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOKRN_ISSET_ID);
  }

  public void setToKrnIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOKRN_ISSET_ID, value);
  }

  public short getToTi() {
    return this.toTi;
  }

  public MeterConfigAmendment setToTi(short toTi) {
    this.toTi = toTi;
    setToTiIsSet(true);
    return this;
  }

  public void unsetToTi() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOTI_ISSET_ID);
  }

  /** Returns true if field toTi is set (has been assigned a value) and false otherwise */
  public boolean isSetToTi() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOTI_ISSET_ID);
  }

  public void setToTiIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOTI_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TO_SGC:
      if (value == null) {
        unsetToSgc();
      } else {
        setToSgc((java.lang.Integer)value);
      }
      break;

    case TO_KRN:
      if (value == null) {
        unsetToKrn();
      } else {
        setToKrn((java.lang.Short)value);
      }
      break;

    case TO_TI:
      if (value == null) {
        unsetToTi();
      } else {
        setToTi((java.lang.Short)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TO_SGC:
      return getToSgc();

    case TO_KRN:
      return getToKrn();

    case TO_TI:
      return getToTi();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TO_SGC:
      return isSetToSgc();
    case TO_KRN:
      return isSetToKrn();
    case TO_TI:
      return isSetToTi();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof MeterConfigAmendment)
      return this.equals((MeterConfigAmendment)that);
    return false;
  }

  public boolean equals(MeterConfigAmendment that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_toSgc = true;
    boolean that_present_toSgc = true;
    if (this_present_toSgc || that_present_toSgc) {
      if (!(this_present_toSgc && that_present_toSgc))
        return false;
      if (this.toSgc != that.toSgc)
        return false;
    }

    boolean this_present_toKrn = true;
    boolean that_present_toKrn = true;
    if (this_present_toKrn || that_present_toKrn) {
      if (!(this_present_toKrn && that_present_toKrn))
        return false;
      if (this.toKrn != that.toKrn)
        return false;
    }

    boolean this_present_toTi = true;
    boolean that_present_toTi = true;
    if (this_present_toTi || that_present_toTi) {
      if (!(this_present_toTi && that_present_toTi))
        return false;
      if (this.toTi != that.toTi)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + toSgc;

    hashCode = hashCode * 8191 + toKrn;

    hashCode = hashCode * 8191 + toTi;

    return hashCode;
  }

  @Override
  public int compareTo(MeterConfigAmendment other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetToSgc()).compareTo(other.isSetToSgc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetToSgc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.toSgc, other.toSgc);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetToKrn()).compareTo(other.isSetToKrn());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetToKrn()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.toKrn, other.toKrn);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetToTi()).compareTo(other.isSetToTi());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetToTi()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.toTi, other.toTi);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("MeterConfigAmendment(");
    boolean first = true;

    sb.append("toSgc:");
    sb.append(this.toSgc);
    first = false;
    if (!first) sb.append(", ");
    sb.append("toKrn:");
    sb.append(this.toKrn);
    first = false;
    if (!first) sb.append(", ");
    sb.append("toTi:");
    sb.append(this.toTi);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'toSgc' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'toKrn' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'toTi' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MeterConfigAmendmentStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MeterConfigAmendmentStandardScheme getScheme() {
      return new MeterConfigAmendmentStandardScheme();
    }
  }

  private static class MeterConfigAmendmentStandardScheme extends org.apache.thrift.scheme.StandardScheme<MeterConfigAmendment> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MeterConfigAmendment struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TO_SGC
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.toSgc = iprot.readI32();
              struct.setToSgcIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TO_KRN
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.toKrn = iprot.readI16();
              struct.setToKrnIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TO_TI
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.toTi = iprot.readI16();
              struct.setToTiIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetToSgc()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'toSgc' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetToKrn()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'toKrn' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetToTi()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'toTi' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, MeterConfigAmendment struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TO_SGC_FIELD_DESC);
      oprot.writeI32(struct.toSgc);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TO_KRN_FIELD_DESC);
      oprot.writeI16(struct.toKrn);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TO_TI_FIELD_DESC);
      oprot.writeI16(struct.toTi);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MeterConfigAmendmentTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MeterConfigAmendmentTupleScheme getScheme() {
      return new MeterConfigAmendmentTupleScheme();
    }
  }

  private static class MeterConfigAmendmentTupleScheme extends org.apache.thrift.scheme.TupleScheme<MeterConfigAmendment> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MeterConfigAmendment struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.toSgc);
      oprot.writeI16(struct.toKrn);
      oprot.writeI16(struct.toTi);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MeterConfigAmendment struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.toSgc = iprot.readI32();
      struct.setToSgcIsSet(true);
      struct.toKrn = iprot.readI16();
      struct.setToKrnIsSet(true);
      struct.toTi = iprot.readI16();
      struct.setToTiIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

