package com.gc.common;


import java.util.List;
import java.util.Random;

public class Strings {
	
	public static final String Empty = "";	
	
	public static String left(String s, int len) {
		return s.substring(0, len);
	}
	
	public static String right(String s, int len) {
		return s.substring(s.length() - len, s.length());
	}
	
	public static String trim(String s) {
		return s != null ? s.trim() : s;
	}
	
	public static String addDoubleQuotation(Object s) {
		return "\"" + replaceBlank(s + "") + "\"";
	}
	
	public static String replace(String s, String newStr, String... oldStr) {
		if (!Strings.isNullOrEmpty(s) && oldStr != null) {
			for (String i : oldStr) {
				s = s.replace(i, newStr);
			}
		}
		return s;
	}
	
	public static String implode(String separator, Object... objects) {
		if (objects != null && objects.length > 0) {
			String result = "";
			for (int i = 0;i < objects.length;++i) {
				result += (i == 0 ? objects[i] : separator + objects[i]);
			}
			return result;
		}
		return "";
	}

	public static String generate(String s, int count) {
		String result = "";
		for (int i = 0;i < count;++i) {
			result += s;
		}
		return result;
	}
	
	public static String SBCToDBC(String QJstr) {
	     String outStr="";
	     String Tstr="";
	     byte[] b=null;

	     for(int i=0;i<QJstr.length();i++) {   
	      try {
	       Tstr=QJstr.substring(i,i+1);
	       b=Tstr.getBytes("unicode");
	      }
	      catch(java.io.UnsupportedEncodingException e) {
	       e.printStackTrace();
	      }     
	   
	      if (b[3]==-1) {
	       b[2]=(byte)(b[2]+32);
	       b[3]=0;      
	        
	       try {      
	        outStr=outStr+new String(b,"unicode");
	       }
	       catch(java.io.UnsupportedEncodingException e) {
	        e.printStackTrace();
	       }      
	      }else outStr=outStr+Tstr;
	     }
	     return outStr; 
	}
	
	public static String implode(String separator, List<String> objects) {
		if (objects != null && objects.size() > 0) {
			String result = "";
			for (int i = 0;i < objects.size();++i) {
				result += (i == 0 ? objects.get(i) : separator + objects.get(i));
			}
			return result;
		}
		return "";
	}
	
	public static String[] explode(String separator, String s) {
		return s.split(separator);
	}
	
	public static String getNumber(String s) {
		if (!Strings.isNullOrEmpty(s)) {
			char[] chs = s.toCharArray();
			String result = "";
			for (char c : chs) {
				if (c >= '0' && c <= '9') {
					result += c;
				}
			}
			return result;
		}
		return "";
	}
	
	public static String ucfirst(String s) {
		if (!Strings.isNullOrEmpty(s)) {
			char c = s.charAt(0);
			if (c >= 'a' && c <= 'z') {
				return String.valueOf(c).toUpperCase() 
				+ s.substring(1);
			}
		}
		return s;
	}
	
	public static String lcfirst(String s) {
		if (!Strings.isNullOrEmpty(s)) {
			char c = s.charAt(0);
			if (c >= 'A' && c <= 'Z') {
				return String.valueOf(c).toLowerCase()
				+ s.substring(1);
			}
		}
		return s;
	}
	
	public static String ucwords(String s) {
		if (!Strings.isNullOrEmpty(s)) {
			String[] arr = s.split("\\s");
			String result = "";
			boolean flag = false;
			for (String v : arr) {
				flag = true;
				result += " ";
				if (!Strings.isNullOrEmpty(v)) {
					result += Strings.ucfirst(v);
				}
				else {
					result += v;
				}
			}
			return flag ? result.substring(1) : result;
		}
		return s;
	}
	
	public static String lcwords(String s) {
		if (!Strings.isNullOrEmpty(s)) {
			String[] arr = s.split("\\s");
			String result = "";
			boolean flag = false;
			for (String v : arr) {
				flag = true;
				result += " ";
				if (!Strings.isNullOrEmpty(v)) {
					result += Strings.lcfirst(v);
				}
				else {
					result += v;
				}
			}
			return flag ? result.substring(1) : result;
		}
		return s;
	}
	
	public static boolean isNullOrEmpty(String value) {
		return value == null || "".equals(value.trim());
	}
	
	public static boolean isNullOrEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if (value instanceof String) {
			return "".equals(Strings.trim(String.valueOf(value)));
		}
		return false;
	}
	
	public static boolean itemHasNullOrEmpty(String... strings) {
		if (strings == null) {
			return true;
		}
		for (int i = 0;i < strings.length;++i) {
			if (isNullOrEmpty(strings[i])) {
				return true;
			}
		}
		return false;
	}
	
	private static char[] DEFAULT_HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	public static String random(int len) {
        return random(DEFAULT_HEX_CHAR, len);
    }
	
	public static String random(char[] chars, int len) {
		String s = "";
        Random rander = new Random(System.currentTimeMillis());
        for(int i = 0; i< len; ++i) {
            s += chars[rander.nextInt(chars.length)];
        }
        return s;
	}

    /**
     * 姣旇緝涓や釜瀛楃涓诧紙澶у皬鍐欐晱鎰燂級銆�
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "abc")  = false
     * StringUtil.equals("abc", null)  = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1 瑕佹瘮杈冪殑瀛楃涓�?1
     * @param str2 瑕佹瘮杈冪殑瀛楃涓�?2
     *
     * @return 濡傛灉涓や釜瀛楃涓茬浉鍚岋紝鎴栬�呴兘鏄�<code>null</code>锛屽垯杩斿洖<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 姣旇緝涓や釜瀛楃涓诧紙澶у皬鍐欎笉鏁忔劅锛夈��
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1 瑕佹瘮杈冪殑瀛楃涓�?1
     * @param str2 瑕佹瘮杈冪殑瀛楃涓�?2
     *
     * @return 濡傛灉涓や釜瀛楃涓茬浉鍚岋紝鎴栬�呴兘鏄�<code>null</code>锛屽垯杩斿洖<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 妫�鏌ュ瓧绗︿覆鏄惁鏄┖鐧斤�?<code>null</code>銆佺┖�?�楃涓�?<code>""</code>鎴栧彧鏈夌┖鐧藉瓧绗︺��?
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 瑕佹鏌ョ殑瀛楃涓�?
     *
     * @return 濡傛灉涓虹┖鐧�, 鍒欒繑鍥�?<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 妫�鏌ュ瓧绗︿覆鏄惁涓嶆槸绌虹櫧锛�?<code>null</code>銆佺┖�?�楃涓�?<code>""</code>鎴栧彧鏈夌┖鐧藉瓧绗︺��?
     * <pre>
     * StringUtil.isBlank(null)      = false
     * StringUtil.isBlank("")        = false
     * StringUtil.isBlank(" ")       = false
     * StringUtil.isBlank("bob")     = true
     * StringUtil.isBlank("  bob  ") = true
     * </pre>
     *
     * @param str 瑕佹鏌ョ殑瀛楃涓�?
     *
     * @return 濡傛灉涓虹┖鐧�, 鍒欒繑鍥�?<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 妫�鏌ュ瓧绗︿覆鏄惁涓�?<code>null</code>鎴栫┖�?�楃涓�?<code>""</code>銆�
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str 瑕佹鏌ョ殑瀛楃涓�?
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�?<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * 妫�鏌ュ瓧绗︿覆鏄惁涓嶆槸<code>null</code>鍜岀┖�?�楃涓�?<code>""</code>銆�
     * <pre>
     * StringUtil.isEmpty(null)      = false
     * StringUtil.isEmpty("")        = false
     * StringUtil.isEmpty(" ")       = true
     * StringUtil.isEmpty("bob")     = true
     * StringUtil.isEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 瑕佹鏌ョ殑瀛楃涓�?
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�?<code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    /**
     * 鍦ㄥ瓧绗︿覆涓煡鎵炬寚瀹氬瓧绗︿覆锛屽苟杩斿洖绗竴涓尮閰嶇殑绱㈠紩鍊笺�傚鏋滃瓧绗︿覆涓�?<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str 瑕佹壂鎻忕殑瀛楃涓�?
     * @param searchStr 瑕佹煡鎵剧殑瀛楃涓�?
     *
     * @return 绗竴涓尮閰嶇殑绱㈠紩鍊笺�傚鏋滃瓧绗︿覆涓�?<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>
     */
    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.indexOf(searchStr);
    }

    /**
     * 鍦ㄥ瓧绗︿覆涓煡鎵炬寚瀹氬瓧绗︿覆锛屽苟杩斿洖绗竴涓尮閰嶇殑绱㈠紩鍊笺�傚鏋滃瓧绗︿覆涓�?<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     * <pre>
     * StringUtil.indexOf(null, *, *)          = -1
     * StringUtil.indexOf(*, null, *)          = -1
     * StringUtil.indexOf("", "", 0)           = 0
     * StringUtil.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtil.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtil.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtil.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtil.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtil.indexOf("aabaabaa", "b", -1) = 2
     * StringUtil.indexOf("aabaabaa", "", 2)   = 2
     * StringUtil.indexOf("abc", "", 9)        = 3
     * </pre>
     *
     * @param str 瑕佹壂鎻忕殑瀛楃涓�?
     * @param searchStr 瑕佹煡鎵剧殑瀛楃涓�?
     * @param startPos 寮�濮嬫悳绱㈢殑绱㈠紩鍊硷紝濡傛灉灏忎簬0锛屽垯鐪嬩綔0
     *
     * @return 绗竴涓尮閰嶇殑绱㈠紩鍊笺�傚鏋滃瓧绗︿覆涓�?<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>
     */
    public static int indexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        // JDK1.3鍙婁互涓嬬増鏈殑bug锛氫笉鑳芥纭鐞嗕笅闈㈢殑鎯呭喌
        if ((searchStr.length() == 0) && (startPos >= str.length())) {
            return str.length();
        }

        return str.indexOf(searchStr, startPos);
    }

    /**
     * 鍙栨寚�?�氬瓧绗︿覆鐨勫瓙涓层��?
     * 
     * <p>
     * 璐熺殑绱㈠紩浠ｈ〃浠庡熬閮ㄥ紑濮嬭绠椼�傚鏋滃瓧绗︿覆涓�?<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     * <pre>
     * StringUtil.substring(null, *, *)    = null
     * StringUtil.substring("", * ,  *)    = "";
     * StringUtil.substring("abc", 0, 2)   = "ab"
     * StringUtil.substring("abc", 2, 0)   = ""
     * StringUtil.substring("abc", 2, 4)   = "c"
     * StringUtil.substring("abc", 4, 6)   = ""
     * StringUtil.substring("abc", 2, 2)   = ""
     * StringUtil.substring("abc", -2, -1) = "b"
     * StringUtil.substring("abc", -4, 2)  = "ab"
     * </pre>
     * </p>
     *
     * @param str 瀛楃涓�?
     * @param start 璧峰绱㈠紩锛屽鏋�?负璐熸暟锛岃〃绀轰粠灏鹃儴璁＄畻
     * @param end 缁撴潫绱㈠紩锛堜笉鍚級锛屽鏋�?负璐熸暟锛岃〃绀轰粠灏鹃儴璁＄畻
     *
     * @return 瀛愪覆锛屽鏋滃師濮嬩覆涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 妫�鏌ュ瓧绗︿覆涓槸鍚�?寘鍚寚瀹氱殑�?�楃涓层�傚鏋滃瓧绗︿覆涓�<code>null</code>锛屽皢杩斿洖<code>false</code>銆�
     * <pre>
     * StringUtil.contains(null, *)     = false
     * StringUtil.contains(*, null)     = false
     * StringUtil.contains("", "")      = true
     * StringUtil.contains("abc", "")   = true
     * StringUtil.contains("abc", "a")  = true
     * StringUtil.contains("abc", "z")  = false
     * </pre>
     *
     * @param str 瑕佹壂鎻忕殑瀛楃涓�?
     * @param searchStr 瑕佹煡鎵剧殑瀛楃涓�?
     *
     * @return 濡傛灉鎵惧埌锛屽垯杩斿洖<code>true</code>
     */
    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return false;
        }

        return str.indexOf(searchStr) >= 0;
    }

    /**
     * <p>Checks if the String contains only unicode digits.
     * A decimal point is not a unicode digit and returns false.</p>
     *
     * <p><code>null</code> will return <code>false</code>.
     * An empty String ("") will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    public static String replaceBlank(String str) {
    	/*if (str != null) {
    		Pattern p = Pattern.compile("\\s*|\r|\n");
    		return p.matcher(str).replaceAll("");
    	}*/
    	return str;
    }
    
    public static String escape(String src) {  
        int i;  
        char j;  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length() * 6);  
        for (i = 0; i < src.length(); i++) {  
            j = src.charAt(i);  
            if (Character.isDigit(j) || Character.isLowerCase(j)  
                    || Character.isUpperCase(j))  
                tmp.append(j);  
            else if (j < 256) {  
                tmp.append("%");  
                if (j < 16)  
                    tmp.append("0");  
                tmp.append(Integer.toString(j, 16));  
            } else {  
                tmp.append("%u");  
                tmp.append(Integer.toString(j, 16));  
            }  
        }  
        return tmp.toString();  
    }
    
    public static String unescape(String src) {  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length());  
        int lastPos = 0, pos = 0;  
        char ch;  
        while (lastPos < src.length()) {  
            pos = src.indexOf("%", lastPos);  
            if (pos == lastPos) {  
                if (src.charAt(pos + 1) == 'u') {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 2, pos + 6), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 6;  
                } else {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 1, pos + 3), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 3;  
                }  
            } else {  
                if (pos == -1) {  
                    tmp.append(src.substring(lastPos));  
                    lastPos = src.length();  
                } else {  
                    tmp.append(src.substring(lastPos, pos));  
                    lastPos = pos;  
                }  
            }  
        }  
        return tmp.toString();  
    }
    
    public static void main(String[] args) {
    	String s = "锛�#锛屼腑闂翠汉";
    	System.out.println(SBCToDBC(s));
    }
	
}
