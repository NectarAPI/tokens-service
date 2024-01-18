/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ke.co.nectar.hsm.prism;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class tokenConstants {

  public static final java.lang.String ApiVersion = "1.1";

  public static final java.lang.String PrintableAsciiRe = "^[\\x20-\\x7e]*$";

  public static final java.lang.String IdentMatchRe = "^[a-zA-Z0-9][a-zA-Z0-9_\\-\\.,]{0,39}$";

  public static final java.util.Map<java.lang.String,java.lang.String> ApiErrors = new java.util.HashMap<java.lang.String,java.lang.String>();
  static {
    ApiErrors.put("EAuthentication.Permission", "Access denied: accessToken does not have permission '{0}'");
    ApiErrors.put("EAuthentication.Token", "Authentication failed (bad accessToken: {0})");
    ApiErrors.put("ECacheMiss", "The requested entry was not found in the cache");
    ApiErrors.put("EKeystore.NotFound", "Vending Key not available for SGC={0},KRN={1} (reason: {2})");
    ApiErrors.put("EParamRange", "Parameter '{0}' out of range: got '{1}', expected {2}");
    ApiErrors.put("EPrefRange", "Preference '{0}' out of range: got '{1}', expected {2}");
    ApiErrors.put("EService", "Unhandled exception '{0}': {1}");
    ApiErrors.put("EService.Disabled", "Feature disabled: {0}");
    ApiErrors.put("ESignin.Authentication", "Sign-in failed: authentication failed for realm='{0}' username='{1}'");
    ApiErrors.put("ESignin.Permission", "Sign-in failed: realm='{0}' username='{1}' does not have permission '{2}'");
    ApiErrors.put("ESignin.Realm", "Sign-in failed: unknown realm='{0}'");
    ApiErrors.put("ESignin.Subject", "Sign-in failed: unknown username='{1}' in realm='{0}'");
    ApiErrors.put("ESignin.Version", "This service (API version={0}) does not support your client's API version='{1}'");
    ApiErrors.put("ESm", "Security Module error '{0}': {1}");
    ApiErrors.put("ESts.DrnOrPan", "Parameter '{0}' is an invalid meter DRN/PAN (reason: {2}); got '{1}'");
    ApiErrors.put("ESts.DrnOrPan.CheckDigit", "Parameter '{0}' is a meter DRN/PAN with bad check digit(s); got '{1}'");
    ApiErrors.put("ESts.IdRecord", "Parameter '{0}' is an invalid IDRecord/Record2 (reason: {2}); got '{1}'");
    ApiErrors.put("ESts.IdRecord.Expired", "Meter identification has expired (YYMM='{0}')");
    ApiErrors.put("ESts.VkBeforeBdt", "Attempt to use Vending Key before its Base Date");
    ApiErrors.put("ESts.VkExpired", "Vending Key has expired (TokenID exceeds KeyExpiryNumber)");
    ApiErrors.put("EVerify.Crc", "Invalid token: CRC error");
    ApiErrors.put("EVerify.DdtkCredit", "Invalid token: Class 0 token encrypted under a DDTK");
    ApiErrors.put("EVerify.KeyExpired", "Invalid token: TokenID out of range for Vending Key KeyExpiryNumber");
    ApiErrors.put("EVerify.Ok", "(Not an error) Token is valid");
  }

}