package com.hydra.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BankCardUtil {
    public static final int FILL         = 9;
    public static final int DEFAULT_SIZE = 5;

    public static List<String> getCards(String bankName, String cardType, int times) {
        List<String> res = null;
        Map<String, List<String>> bank = bankInfo.get(bankName);
        if (null != bank) {
            List<String> cards = bank.get(cardType);
            int size = 0;
            if (null != cards && (size = cards.size()) > 0) {
                String cardNo = cards.get(Integer.valueOf(RandomUtil.get().nextInt(size)));
                res = getSome(cardNo, times);
            }
        }
        return res;
    }

    public static Collection<String> getBanks() {
        Collection<String> res = bankInfo.keySet();
        return res;
    }

    public static String getSingleCard(String header, int length) {
        return getSingleCard(header, length, 1);
    }

    public static String getSingleCard(String header, int length, int times) {
        String res = null;
        int ran = length - header.length() - 1;
        String base = header + getRandom(ran);
        res = base + getBankCardCheckCode(base);
        return res;
    }

    public static List<String> getSome(String source, int times) {
        List<String> res = new ArrayList<String>();
        List<String> used = new ArrayList<String>(times);
        log.info("Source is: {}", source);
        int len = source.length();
        String base = source.substring(0, len - 6) + getRandom(2);
        for (int i = 0; i < times; i++) {
            String r = getRandom(3);
            while (used.contains(r)) {
                r = getRandom(3);
            }
            used.add(r);
            String s = base + r;
            String cardNo = s + getBankCardCheckCode(s);
            res.add(cardNo);
        }
        log.info("And targets are: {}", res);
        return res;
    }

    public static String getRandom(int len) {
        StringBuilder sb = new StringBuilder(FILL);
        for (int i = 0; i < len; i++) {
            sb.append(FILL);
        }
        String sMax = sb.toString();
        int max = Integer.valueOf(sMax);
        int ran = RandomUtil.get().nextInt(max);
        return String.format("%0" + len + "d", ran);
    }

    /**
     * 校验银行卡卡号
     * 
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * 
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            throw new IllegalArgumentException("Bank card code must be number!");
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    private static final Map<String, Map<String, List<String>>> bankInfo = new HashMap<String, Map<String, List<String>>>();

    static {
        Map<String, List<String>> js = new HashMap<String, List<String>>();
        List<String> l_js_1 = new ArrayList<String>();
        l_js_1.add("4340610010723531");
        l_js_1.add("6227003814240009971");
        js.put("1", l_js_1);
        List<String> l_js_0 = new ArrayList<String>();
        l_js_0.add("4367450036524979");
        l_js_0.add("4367480058739742");
        l_js_0.add("5324505094763378");
        js.put("0", l_js_0);
        bankInfo.put("建设银行", js);
        Map<String, List<String>> gs = new HashMap<String, List<String>>();
        List<String> l_gs_1 = new ArrayList<String>();
        l_gs_1.add("6222000200126512417");
        l_gs_1.add("6222024402048307833");
        l_gs_1.add("6222080200017536585");
        l_gs_1.add("9558820200008537641");
        gs.put("1", l_gs_1);
        List<String> l_gs_0 = new ArrayList<String>();
        l_gs_0.add("4270200043697334");
        l_gs_0.add("5309883272312576");
        l_gs_0.add("6222320030412796");
        gs.put("0", l_gs_0);
        bankInfo.put("工商银行", gs);
        Map<String, List<String>> zh = new HashMap<String, List<String>>();
        List<String> l_zh_1 = new ArrayList<String>();
        l_zh_1.add("6013823100028781375");
        zh.put("1", l_zh_1);
        List<String> l_zh_0 = new ArrayList<String>();
        l_zh_0.add("4096666941395514");
        l_zh_0.add("5149586594890237");
        l_zh_0.add("5257461150620600");
        l_zh_0.add("6227604824776408");
        zh.put("0", l_zh_0);
        bankInfo.put("中国银行", zh);
        Map<String, List<String>> nh = new HashMap<String, List<String>>();
        List<String> l_nh_1 = new ArrayList<String>();
        l_nh_1.add("6228480462784891519");
        nh.put("1", l_nh_1);
        List<String> l_nh_0 = new ArrayList<String>();
        l_nh_0.add("4041170048154805");
        l_nh_0.add("5194130005031331");
        l_nh_0.add("6228370094576568");
        nh.put("0", l_nh_0);
        bankInfo.put("农业银行", nh);
        Map<String, List<String>> zx = new HashMap<String, List<String>>();
        List<String> l_zx_1 = new ArrayList<String>();
        l_zx_1.add("6226901407428345");
        zx.put("1", l_zx_1);
        List<String> l_zx_0 = new ArrayList<String>();
        l_zx_0.add("4033918011997547");
        l_zx_0.add("4033928004853763");
        l_zx_0.add("5182120009649307");
        l_zx_0.add("6226809001695898");
        l_zx_0.add("6226890000557560");
        zx.put("0", l_zx_0);
        bankInfo.put("中信银行", zx);
        Map<String, List<String>> yc = new HashMap<String, List<String>>();
        List<String> l_yc_1 = new ArrayList<String>();
        l_yc_1.add("6210982900004747996");
        l_yc_1.add("6221881000094699568");
        yc.put("1", l_yc_1);
        List<String> l_yc_0 = new ArrayList<String>();
        l_yc_0.add("6228100007052872");
        l_yc_0.add("6228110007231079");
        yc.put("0", l_yc_0);
        bankInfo.put("邮政储蓄", yc);
        Map<String, List<String>> zs = new HashMap<String, List<String>>();
        List<String> l_zs_1 = new ArrayList<String>();
        l_zs_1.add("6225880223700338");
        zs.put("1", l_zs_1);
        List<String> l_zs_0 = new ArrayList<String>();
        l_zs_0.add("3568891113487219");
        l_zs_0.add("4392250802469253");
        l_zs_0.add("4392268003827798");
        l_zs_0.add("5187180003088428");
        zs.put("0", l_zs_0);
        bankInfo.put("招商银行", zs);
        Map<String, List<String>> ms = new HashMap<String, List<String>>();
        List<String> l_ms_1 = new ArrayList<String>();
        l_ms_1.add("4155990135715750");
        ms.put("1", l_ms_1);
        List<String> l_ms_0 = new ArrayList<String>();
        l_ms_0.add("3568570030789267");
        l_ms_0.add("4218700015668259");
        ms.put("0", l_ms_0);
        bankInfo.put("民生银行", ms);
        Map<String, List<String>> pa = new HashMap<String, List<String>>();
        List<String> l_pa_1 = new ArrayList<String>();
        l_pa_1.add("6225380091596157");
        l_pa_1.add("6222980102715929");
        l_pa_1.add("6229890000661757");
        l_pa_1.add("6270660100331793");
        pa.put("1", l_pa_1);
        List<String> l_pa_0 = new ArrayList<String>();
        l_pa_0.add("5268550499368223");
        l_pa_0.add("6221550404919999");
        l_pa_0.add("6225250000619418");
        l_pa_0.add("6225260114404540");
        pa.put("0", l_pa_0);
        bankInfo.put("平安银行", pa);
        Map<String, List<String>> jt = new HashMap<String, List<String>>();
        List<String> l_jt_1 = new ArrayList<String>();
        l_jt_1.add("6222601210007608005");
        jt.put("1", l_jt_1);
        List<String> l_jt_0 = new ArrayList<String>();
        l_jt_0.add("4581230211653730");
        l_jt_0.add("5201690210658435");
        l_jt_0.add("6222530917587029");
        jt.put("0", l_jt_0);
        bankInfo.put("交通银行", jt);
        Map<String, List<String>> xy = new HashMap<String, List<String>>();
        List<String> l_xy_1 = new ArrayList<String>();
        l_xy_1.add("622909326671911115");
        xy.put("1", l_xy_1);
        List<String> l_xy_0 = new ArrayList<String>();
        l_xy_0.add("5230367001990104");
        l_xy_0.add("6229010331404104");
        l_xy_0.add("4512893361899108");
        l_xy_0.add("4512900618845113");
        xy.put("0", l_xy_0);
        bankInfo.put("兴业银行", xy);
        Map<String, List<String>> gf = new HashMap<String, List<String>>();
        List<String> l_gf_1 = new ArrayList<String>();
        l_gf_1.add("6225684221000195117");
        gf.put("1", l_gf_1);
        List<String> l_gf_0 = new ArrayList<String>();
        l_gf_0.add("4063661372103528");
        l_gf_0.add("5201521360714381");
        l_gf_0.add("5203821378769577");
        l_gf_0.add("6225571350116329");
        gf.put("0", l_gf_0);
        bankInfo.put("广发银行", gf);
        Map<String, List<String>> gd = new HashMap<String, List<String>>();
        List<String> l_gd_1 = new ArrayList<String>();
        l_gd_1.add("6226642119632366");
        gd.put("1", l_gd_1);
        List<String> l_gd_0 = new ArrayList<String>();
        l_gd_0.add("3568400000318881");
        l_gd_0.add("4062522833838741");
        l_gd_0.add("4816990005072317");
        l_gd_0.add("6226580006171694");
        l_gd_0.add("6226592000283482");
        gd.put("0", l_gd_0);
        bankInfo.put("光大银行", gd);
        Map<String, List<String>> hx = new HashMap<String, List<String>>();
        List<String> l_hx_1 = new ArrayList<String>();
        l_hx_1.add("6226332625950839");
        hx.put("1", l_hx_1);
        List<String> l_hx_0 = new ArrayList<String>();
        l_hx_0.add("5287080005395186");
        l_hx_0.add("6226371102132271");
        l_hx_0.add("6226380000659327");
        hx.put("0", l_hx_0);
        bankInfo.put("华夏银行", hx);
        Map<String, List<String>> bj = new HashMap<String, List<String>>();
        List<String> l_bj_1 = new ArrayList<String>();
        l_bj_1.add("6029693050247069");
        l_bj_1.add("6210300120143075");
        bj.put("1", l_bj_1);
        List<String> l_bj_0 = new ArrayList<String>();
        l_bj_0.add("5220010100468570");
        l_bj_0.add("6221639902110357");
        bj.put("0", l_bj_0);
        bankInfo.put("北京银行", bj);
    }

    // 静态工具类，防误生成
    private BankCardUtil() {
        throw new UnsupportedOperationException();
    }
}
