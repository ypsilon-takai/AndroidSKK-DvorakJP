package jp.deadend.noname.skk.engine

import jp.deadend.noname.skk.isVowel

object RomajiConverter {
    private val mRomajiMap = mapOf(
        "a"  to "あ", "i"  to "い", "u"  to "う", "e"  to "え", "o"  to "お",
        "ka" to "か", "ki" to "き", "ku" to "く", "ke" to "け", "ko" to "こ",
        "ca" to "か", "ci" to "き", "cu" to "く", "ce" to "け", "co" to "こ",
        "sa" to "さ", "si" to "し", "su" to "す", "se" to "せ", "so" to "そ",
        "ta" to "た", "ti" to "ち", "tu" to "つ", "te" to "て", "to" to "と",
        "na" to "な", "ni" to "に", "nu" to "ぬ", "ne" to "ね", "no" to "の",
        "ha" to "は", "hi" to "ひ", "hu" to "ふ", "he" to "へ", "ho" to "ほ",
        "ma" to "ま", "mi" to "み", "mu" to "む", "me" to "め", "mo" to "も",
        "ya" to "や", "yi" to "い", "yu" to "ゆ", "ye" to "いぇ", "yo" to "よ",
        "ra" to "ら", "ri" to "り", "ru" to "る", "re" to "れ", "ro" to "ろ",
        "wa" to "わ", "wi" to "うぃ", "we" to "うぇ", "wo" to "を", "nn" to "ん",
        "ga" to "が", "gi" to "ぎ", "gu" to "ぐ", "ge" to "げ", "go" to "ご",
        "za" to "ざ", "zi" to "じ", "zu" to "ず", "ze" to "ぜ", "zo" to "ぞ",
        "da" to "だ", "di" to "ぢ", "du" to "づ", "de" to "で", "do" to "ど",
        "ba" to "ば", "bi" to "び", "bu" to "ぶ", "be" to "べ", "bo" to "ぼ",
        "pa" to "ぱ", "pi" to "ぴ", "pu" to "ぷ", "pe" to "ぺ", "po" to "ぽ",
        "va" to "う゛ぁ", "vi" to "う゛ぃ", "vu" to "う゛", "ve" to "う゛ぇ", "vo" to "う゛ぉ",

        "xa" to "ぁ", "xi" to "ぃ", "xu" to "ぅ", "xe" to "ぇ", "xo" to "ぉ",
        "xtu" to "っ", "xke" to "ヶ", "xce" to "ヶ",
        "cha" to "ちゃ", "chi" to "ち", "chu" to "ちゅ", "che" to "ちぇ", "cho" to "ちょ",
        "fa" to "ふぁ", "fi" to "ふぃ", "fu" to "ふ", "fe" to "ふぇ", "fo" to "ふぉ",

        "xya" to "ゃ",   "xyu" to "ゅ",   "xyo" to "ょ",
        "kya" to "きゃ", "kyu" to "きゅ", "kyo" to "きょ",
        "cna" to "きゃ", "cnu" to "きゅ", "cno" to "きょ",
        "gya" to "ぎゃ", "gyu" to "ぎゅ", "gyo" to "ぎょ",
        "gna" to "ぎゃ", "gnu" to "ぎゅ", "gno" to "ぎょ",
        "sya" to "しゃ", "syu" to "しゅ", "syo" to "しょ",
        "sha" to "しゃ", "shi" to "し",   "shu" to "しゅ", "she" to "しぇ", "sho" to "しょ",
        "ja"  to "じゃ", "ji"  to "じ",   "ju"  to "じゅ", "je"  to "じぇ", "jo"  to "じょ",
        "zha" to "じゃ", "zhi" to "じ",   "zhu" to "じゅ", "zhe" to "じぇ", "zho" to "じょ",
        "cha" to "ちゃ", "chi" to "ち",   "chu" to "ちゅ", "che" to "ちぇ", "cho" to "ちょ",
        "tya" to "ちゃ", "tyu" to "ちゅ", "tye" to "ちぇ", "tyo" to "ちょ",
        "tna" to "ちゃ", "tnu" to "ちゅ", "tne" to "ちぇ", "tno" to "ちょ",
        "tha" to "てゃ", "thi" to "てぃ", "thu" to "てゅ", "the" to "てぇ", "tho" to "てょ",
        "dha" to "でゃ", "dhi" to "でぃ", "dhu" to "でゅ", "dhe" to "でぇ", "dho" to "でょ",
        "dya" to "ぢゃ", "dyi" to "ぢぃ", "dyu" to "ぢゅ", "dye" to "ぢぇ", "dyo" to "ぢょ",
        "dna" to "ぢゃ", "dni" to "ぢぃ", "dnu" to "ぢゅ", "dne" to "ぢぇ", "dno" to "ぢょ",
        "nya" to "にゃ", "nyu" to "にゅ", "nyo" to "にょ",
        "nha" to "にゃ", "nhu" to "にゅ", "nho" to "にょ",
        "hya" to "ひゃ", "hyu" to "ひゅ", "hyo" to "ひょ",
        "hna" to "ひゃ", "hnu" to "ひゅ", "hno" to "ひょ",
        "pya" to "ぴゃ", "pyu" to "ぴゅ", "pyo" to "ぴょ",
        "pna" to "ぴゃ", "pnu" to "ぴゅ", "pno" to "ぴょ",
        "bya" to "びゃ", "byu" to "びゅ", "byo" to "びょ",
        "bna" to "びゃ", "bnu" to "びゅ", "bno" to "びょ",
        "mya" to "みゃ", "myu" to "みゅ", "myo" to "みょ",
        "mna" to "みゃ", "mnu" to "みゅ", "mno" to "みょ",
        "rya" to "りゃ", "ryu" to "りゅ", "rye" to "りぇ", "ryo" to "りょ",
        "rha" to "りゃ", "rhu" to "りゅ", "rhe" to "りぇ", "rho" to "りょ",

        // 撥音拡張
        "a;" to "あん", "ix"  to "いん", "uk" to "うん", "ej" to "えん", "oq" to "おん",
        "k;" to "かん", "kx" to "きん", "kk" to "くん", "kj" to "けん", "kq" to "こん",
        "c;" to "かん", "cx" to "きん", "ck" to "くん", "cj" to "けん", "cq" to "こん",
        "s;" to "さん", "sx" to "しん", "sk" to "すん", "sj" to "せん", "sq" to "そん",
        "t;" to "たん", "tx" to "ちん", "tk" to "つん", "tj" to "てん", "tq" to "とん",
        "n;" to "なん", "nx" to "にん", "nk" to "ぬん", "nj" to "ねん", "nq" to "のん",
        "h;" to "はん", "hx" to "ひん", "hk" to "ふん", "hj" to "へん", "hq" to "ほん",
        "m;" to "まん", "mx" to "みん", "mk" to "むん", "mj" to "めん", "mq" to "もん",
        "y;" to "やん", "yx" to "いん", "yk" to "ゆん", "yj" to "いぇん", "oq" to "よん",
        "r;" to "らん", "rx" to "りん", "rk" to "るん", "rj" to "れん", "rq" to "ろん",
        "w;" to "わん", "wx" to "うぃん", "wj" to "うぇん",
        "g;" to "がん", "gｘ" to "ぎん", "gk" to "ぐん", "gj" to "げん", "gq" to "ごん",
        "z;" to "ざん", "zｘ" to "じん", "zk" to "ずん", "zj" to "ぜん", "zq" to "ぞん",
        "d;" to "だん", "dｘ" to "ぢん", "dk" to "づん", "dj" to "でん", "dq" to "どん",
        "b;" to "ばん", "bｘ" to "びん", "bk" to "ぶん", "bj" to "べん", "bq" to "ぼん",
        "p;" to "ぱん", "pｘ" to "ぴん", "pk" to "ぷん", "pj" to "ぺん", "pq" to "ぽん",
        "v;" to "う゛ぁん", "vx" to "う゛ぃん", "vk" to "う゛ん", "vj" to "う゛ぇん", "vq" to "う゛ぉん",

        "ch;" to "ちゃん", "chx" to "ちん", "chk" to "ちゅん", "chj" to "ちぇん", "chq" to "ちょん",
        "f;" to "ふぁん", "fx" to "ふぃん", "fk" to "ふん", "fj" to "ふぇん", "fq" to "ふぉん",

        "ky;" to "きゃん", "kyk" to "きゅん", "kyq" to "きょん",
        "cn;" to "きゃん", "cnk" to "きゅん", "cnq" to "きょん",
        "gy;" to "ぎゃん", "gyk" to "ぎゅん", "gyq" to "ぎょん",
        "gn;" to "ぎゃん", "gnk" to "ぎゅん", "gnq" to "ぎょん",
        "sy;" to "しゃん", "syk" to "しゅん", "syq" to "しょん",
        "sh;" to "しゃん", "shx" to "しん",   "shk" to "しゅん", "shj" to "しぇん", "shq" to "しょん",
        "j;"  to "じゃん", "jx"  to "じん",   "jk"  to "じゅん", "jj"  to "じぇん", "jq"  to "じょん",
        "zh;" to "じゃん", "zhx" to "じん",   "zhk" to "じゅん", "zhj" to "じぇん", "zhq" to "じょん",
        "ch;" to "ちゃん", "chx" to "ちん",   "chk" to "ちゅん", "chj" to "ちぇん", "chq" to "ちょん",
        "ty;" to "ちゃん", "tyk" to "ちゅん", "tyj" to "ちぇん", "tyq" to "ちょん",
        "tn;" to "ちゃん", "tnk" to "ちゅん", "tnj" to "ちぇん", "tnq" to "ちょん",
        "th;" to "てゃん", "thx" to "てぃん", "thk" to "てゅん", "thj" to "てぇん", "thq" to "てょん",
        "dh;" to "でゃん", "dhx" to "でぃん", "dhk" to "でゅん", "dhj" to "でぇん", "dhq" to "でょん",
        "dy;" to "ぢゃん", "dyx" to "ぢぃん", "dyk" to "ぢゅん", "dyj" to "ぢぇん", "dyq" to "ぢょん",
        "dn;" to "ぢゃん", "dnx" to "ぢぃん", "dnk" to "ぢゅん", "dnj" to "ぢぇん", "dnq" to "ぢょん",
        "ny;" to "にゃん", "nyk" to "にゅん", "nyq" to "にょん",
        "nh;" to "にゃん", "nhk" to "にゅん", "nhq" to "にょん",
        "hy;" to "ひゃん", "hyk" to "ひゅん", "hyq" to "ひょん",
        "hn;" to "ひゃん", "hnk" to "ひゅん", "hnq" to "ひょん",
        "py;" to "ぴゃん", "pyk" to "ぴゅん", "pyq" to "ぴょん",
        "pn;" to "ぴゃん", "pnk" to "ぴゅん", "pnq" to "ぴょん",
        "by;" to "びゃん", "byk" to "びゅん", "byq" to "びょん",
        "bn;" to "びゃん", "bnk" to "びゅん", "bnq" to "びょん",
        "my;" to "みゃん", "myk" to "みゅん", "myq" to "みょん",
        "mn;" to "みゃん", "mnk" to "みゅん", "mnq" to "みょん",
        "ry;" to "りゃん", "ryk" to "りゅん", "ryj" to "りぇん", "ryq" to "りょん",
        "rh;" to "りゃん", "rhk" to "りゅん", "rhj" to "りぇん", "rhq" to "りょん",

        // 連母音拡張
        "k'" to "かい", "k." to "けい", "k," to "こう",
        "c'" to "かい", "c." to "けい", "c," to "こう",
        "s'" to "さい", "s." to "せい", "s," to "そう",
        "t'" to "たい", "t." to "てい", "t," to "とう",
        "n'" to "ない", "n." to "ねい", "n," to "のう",
        "h'" to "はい", "h." to "へい", "h," to "ほう",
        "m'" to "まい", "m." to "めい", "m," to "もう",
        "y'" to "やい", "y." to "いぇい", "y," to "よう",
        "r'" to "らい", "r." to "れい", "r," to "ろう",
        "w'" to "わい",
        "g'" to "がい", "g." to "げい", "g," to "ごう",
        "z'" to "ざい", "z." to "ぜい", "z," to "ぞう",
        "d'" to "だい", "d." to "でい", "d," to "どう",
        "b'" to "ばい", "b." to "べい", "b," to "ぼう",
        "p'" to "ぱい", "p." to "ぺい", "p," to "ぽう",
        "v'" to "う゛ぁい", "v." to "う゛ぇい", "v," to "う゛ぉう",

        "ch'" to "ちゃい", "ch." to "ちぇい", "ch," to "ちょう",
        "f'" to "ふぁい", "f." to "ふぇい", "f," to "ふぉう",

        "ky'" to "きゃい", "ky," to "きょう",
        "cn'" to "きゃい", "cn," to "きょう",
        "gy'" to "ぎゃい", "gy," to "ぎょう",
        "gn'" to "ぎゃい", "gn," to "ぎょう",
        "sy'" to "しゃい", "sy," to "しょう",
        "sh'" to "しゃい", "sh." to "しぇい", "sh," to "しょう",
        "j'"  to "じゃい", "j."  to "じぇい", "j,"  to "じょう",
        "zh'" to "じゃい", "zh." to "じぇい", "zh," to "じょう",
        "ch'" to "ちゃい", "ch." to "ちぇい", "ch," to "ちょう",
        "ty'" to "ちゃい", "ty." to "ちぇい", "ty," to "ちょう",
        "tn'" to "ちゃい", "tn." to "ちぇい", "tn," to "ちょう",
        "th'" to "てゃい", "th." to "てぇい", "th," to "てょう",
        "dh'" to "でゃい", "dh." to "でぇい", "dh," to "でょう",
        "dy'" to "ぢゃい", "dy." to "ぢぇい", "dy," to "ぢょう",
        "dn'" to "ぢゃい", "dn." to "ぢぇい", "dn," to "ぢょう",
        "ny'" to "にゃい", "ny," to "にょう",
        "nh'" to "にゃい", "nh," to "にょう",
        "hy'" to "ひゃい", "hy," to "ひょう",
        "hn'" to "ひゃい", "hn," to "ひょう",
        "py'" to "ぴゃい", "py," to "ぴょう",
        "pn'" to "ぴゃい", "pn," to "ぴょう",
        "by'" to "びゃい", "by," to "びょう",
        "bn'" to "びゃい", "bn," to "びょう",
        "my'" to "みゃい", "my," to "みょう",
        "mn'" to "みゃい", "mn," to "みょう",
        "ry'" to "りゃい", "ry." to "りぇい", "ry," to "りょう",
        "rh'" to "りゃい", "rh." to "りぇい", "rh," to "りょう",

        // 特殊文字
        "z," to "‥", "z-" to "〜", "z." to "…", "z/" to "・", "z[" to "『",
        "z]" to "』", "zh" to "←", "zj" to "↓", "zk" to "↑", "zl" to "→"
    )

    private val mConsonantMap = mapOf(
        "が" to "g", "ぎ" to "g", "ぐ" to "g", "げ" to "g", "ご" to "g",
        "か" to "k", "き" to "k", "く" to "k", "け" to "k", "こ" to "k",
        "ざ" to "z", "じ" to "z", "ず" to "z", "ぜ" to "z", "ぞ" to "z",
        "さ" to "s", "し" to "s", "す" to "s", "せ" to "s", "そ" to "s",
        "だ" to "d", "ぢ" to "d", "づ" to "d", "で" to "d", "ど" to "d",
        "た" to "t", "ち" to "t", "つ" to "t", "て" to "t", "と" to "t",
        "ば" to "b", "び" to "b", "ぶ" to "b", "べ" to "b", "ぼ" to "b",
        "ぱ" to "p", "ぴ" to "p"," ぷ" to "p", "ぺ" to "p", "ぽ" to "p",
        "は" to "h", "ひ" to "h", "ふ" to "h", "へ" to "h", "ほ" to "h"
    )

    private val mSmallKanaMap = mapOf(
        "あ" to "ぁ", "い" to "ぃ", "う" to "ぅ", "え" to "ぇ", "お" to "ぉ",
        "ぁ" to "あ", "ぃ" to "い", "ぅ" to "う", "ぇ" to "え", "ぉ" to "お",
        "や" to "ゃ", "ゆ" to "ゅ", "よ" to "ょ", "つ" to "っ",
        "ゃ" to "や", "ゅ" to "ゆ", "ょ" to "よ", "っ" to "つ",
        "ア" to "ァ", "イ" to "ィ", "ウ" to "ゥ", "エ" to "ェ", "オ" to "ォ",
        "ァ" to "ア", "ィ" to "イ", "ゥ" to "ウ", "ェ" to "エ", "ォ" to "オ",
        "ヤ" to "ャ", "ユ" to "ュ", "ヨ" to "ョ", "ツ" to "ッ",
        "ャ" to "ヤ", "ュ" to "ユ", "ョ" to "ヨ", "ッ" to "ツ"
    )

    private val mDakutenMap = mapOf(
        "か" to "が", "き" to "ぎ", "く" to "ぐ", "け" to "げ", "こ" to "ご",
        "が" to "か", "ぎ" to "き", "ぐ" to "く", "げ" to "け", "ご" to "こ",
        "さ" to "ざ", "し" to "じ", "す" to "ず", "せ" to "ぜ", "そ" to "ぞ",
        "ざ" to "さ", "じ" to "し", "ず" to "す", "ぜ" to "せ", "ぞ" to "そ",
        "た" to "だ", "ち" to "ぢ", "つ" to "づ", "て" to "で", "と" to "ど",
        "だ" to "た", "ぢ" to "ち", "づ" to "つ", "で" to "て", "ど" to "と",
        "は" to "ば", "ひ" to "び", "ふ" to "ぶ", "へ" to "べ", "ほ" to "ぼ",
        "ば" to "は", "び" to "ひ", "ぶ" to "ふ", "べ" to "へ", "ぼ" to "ほ",
        "カ" to "ガ", "キ" to "ギ", "ク" to "グ", "ケ" to "ゲ", "コ" to "ゴ",
        "ガ" to "カ", "ギ" to "キ", "グ" to "ク", "ゲ" to "ケ", "ゴ" to "コ",
        "サ" to "ザ", "シ" to "ジ", "ス" to "ズ", "セ" to "セ", "ソ" to "ゾ",
        "ザ" to "サ", "ジ" to "シ", "ズ" to "ス", "ゼ" to "ゼ", "ゾ" to "ソ",
        "タ" to "ダ", "チ" to "ヂ", "ツ" to "ヅ", "テ" to "デ", "ト" to "ド",
        "ダ" to "タ", "ヂ" to "チ", "ヅ" to "ツ", "デ" to "テ", "ド" to "ト",
        "ハ" to "バ", "ヒ" to "ビ", "フ" to "ブ", "ヘ" to "ベ", "ホ" to "ボ",
        "バ" to "ハ", "ビ" to "ヒ", "ブ" to "フ", "ベ" to "ヘ", "ボ" to "ホ",
        "ウ" to "ヴ", "ヴ" to "ウ"
    )

    private val mHandakutenMap = mapOf(
        "は" to "ぱ", "ひ" to "ぴ", "ふ" to "ぷ", "へ" to "ぺ", "ほ" to "ぽ",
        "ぱ" to "は", "ぴ" to "ひ", "ぷ" to "ふ", "ぺ" to "へ", "ぽ" to "ほ",
        "ハ" to "パ", "ヒ" to "ピ", "フ" to "プ", "ヘ" to "ペ", "ホ" to "ポ",
        "パ" to "ハ", "ピ" to "ヒ", "プ" to "フ", "ペ" to "ヘ", "ポ" to "ホ"
    )

    fun convert(romaji: String) = mRomajiMap[romaji]
    fun getConsonantForVoiced(kana: String) = mConsonantMap[kana]
    fun convertLastChar(kana: String, type: String) = when (type) {
        SKKEngine.LAST_CONVERTION_SMALL      -> mSmallKanaMap[kana]
        SKKEngine.LAST_CONVERTION_DAKUTEN    -> mDakutenMap[kana]
        SKKEngine.LAST_CONVERTION_HANDAKUTEN -> mHandakutenMap[kana]
        else -> null
    }
    // 1文字目と2文字目を合わせて"ん"・"っ"になるか判定
    // ならなかったらnull
    fun checkSpecialConsonants(first: Char, second: Int) = when {
        (first == 'n') -> if (!isVowel(second) && second != 'n'.code &&
            second != 'y'.code && second != 'h'.code ) {
            "ん"
        } else {
            null
        }
        (first.code == second) -> "っ"
        else -> null
    }
}