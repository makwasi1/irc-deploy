package com.kengamis

import groovy.time.TimeCategory
import org.grails.exceptions.reporting.DefaultStackTraceFilterer
import org.owasp.esapi.ESAPI
import org.owasp.esapi.codecs.MySQLCodec

class Util {

    def grailsApplication
    def springSecurityService
    static def stackFilterer = new DefaultStackTraceFilterer(true)

    static def <T extends Throwable> T sanitize(T e, List<String> otherPackages = []) {
        otherPackages?.each {
            stackFilterer.addInternalPackage(it)
        }

        return stackFilterer.filter(e, true) as T
    }

    static List<Integer> toYearRange(Date date = new Date(), IntRange range) {

        def contextualYear = date.toCalendar().get(Calendar.YEAR)

        def years = range.collect { contextualYear + it }

        return years.reverse()
    }

    static String constructFormTable(def formName) {
        return (formName.replaceAll(" ", "_")).toLowerCase()
    }

    static String tryOxdUnEscape(def d) {

        String s = d?.toString()

        if (s == null || s == 'null') return ''

        HashMap<String, String> swapChars = ["!"   : "bang",
                                             "#"   : "pound",
                                             "\\*" : "star",
                                             "'"   : "apos",
                                             "\""  : "quote",
                                             "%"   : "percent",
                                             "<"   : "lt",
                                             ">"   : "gt",
                                             "="   : "eq",
                                             "/"   : "slash",
                                             "\\\\": "backslash",
                                             "\\." : "dot",
                                             "-"   : "hyphen"]



        s = s.replaceAll(/\s_[0-9]/) { it - '_' }

        if (s ==~ '^(_[0-9])') s = s.replaceFirst('_', '')


        swapChars.each { k, v ->
            def regex = "(?i)_$v"
            s = s.replaceAll(regex, k)
        }

        swapChars.each { k, v ->
            String regex = "${k}_"
            String replacement = "$k "
            s = s.replace(regex, replacement)
        }
        s = s.replaceAll('_', ' ')
        return s
    }

    static String escapeField(String field) {
        if (field.contains('`')) throw new IllegalArgumentException("Illegal table name [$field]")
        return "`$field`"
    }

    static String removeExtraSpace(String string) {
        string.toString().replaceAll(/\s+/, ' ').trim()
    }

    static Long extractId(Map params) {
        extractId(params, 'id')
    }

    static String getSqlWildCard(String search) {
        search = search.split(/\s+/).collect { String s -> "${escapeSql(s)}" }.join('%')
        return "%$search%"

    }

    static Long extractId(Map params, String idField) {
        Long id = -1
        try {
            id = (params[idField] as Long) ?: -1
        } catch (Exception x) {
        }
        return id
    }

    static String escapeSql(String sqlParam) {
        def mySqlEncoder = new MySQLCodec(MySQLCodec.Mode.STANDARD)
        def encoded = ESAPI.encoder().encodeForSQL(mySqlEncoder, sqlParam)
        encoded = encoded.replaceAll(/\\_/, "_")
        return encoded
    }

    static <K, V> V getOrCreate(Map<K, V> map, K k, V defaultValue) {
        def value = map[k]

        if (value) return value

        map[k] = defaultValue
        return defaultValue
    }

    final static int[] illegalChars = [34, 60, 62, 124, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 58, 42, 63, 92, 47] as int[]
    static {
        Arrays.sort(illegalChars);
    }
    public static String cleanFileName(String badFileName) {
        StringBuilder cleanName = new StringBuilder();
        for (int i = 0; i < badFileName.length(); i++) {
            int c = (int) badFileName.charAt(i);
            if (Arrays.binarySearch(illegalChars, c) < 0) {
                cleanName.append(c as char);
            }
        }
        return cleanName.toString();
    }

    static String truncateString(def s, Number num) {
        if (s == null) return ''

        def temp = s.toString()

        if (num >= temp.size())
            return temp
        return temp[0..num - 3] + '...'
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    static String fromNow(Date date) {
        if (!date) return ''
        def d = TimeCategory.minus(date, new Date())
        if (d.days) {
            int days = d.days

            //dsd
            long years = Math.round(days / 365)
            if (years) {
                return "${years.abs()} year${numberEnding years}"
            }

            long months = (long) (days / 30)
            if (months) {
                def roundMonth = Math.round(days / 30)
                return "${roundMonth.abs()} month${numberEnding roundMonth}"
            }

            long weeks = (long) (days / 7)
            if (weeks) {
                if (weeks == 1) {
                    days -= (weeks * 7)
                    def string = "${weeks.abs()} week${numberEnding weeks}"
                    if (days) {
                        string = "$string and $days day${numberEnding days}"
                    }
                    return string
                }
                return "${weeks.abs()} week${numberEnding weeks}"
            }
//          return "${days.abs()}day(s)${d.hours ? ' ' + d.hours.abs() + 'h' : ''}${d.days < 0 ? ' ago' : ''}"
            if (d.hours) {
                return "${days.abs() + (days < 0 ? 0 : 1)} day${numberEnding(days + (days < 0 ? 0 : 1))}"
            }
            return "${days.abs()} day${numberEnding days}"
        }

        if (d.hours) {
            return "${d.hours.abs()} hour${numberEnding d.hours}"
        }

        if (d.minutes) {
            return "${d.minutes.abs()} minute${numberEnding d.minutes}"
        }

        return "now"

    }

    private static def numberEnding(Number number) {
        def end = (number < 0) ? ' ago' : ''
        def absNum = number.abs()
        return (absNum > 1) ? "s$end" : end;
    }
}
