package jp.sourceforge.andjong.mahjong;

import static jp.sourceforge.andjong.mahjong.Hai.*;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

public class AgariScore {
	public static class AgariInfo {
		public int m_han;
		public int m_fu;
		public String[] m_yakuNames;
		public Score m_score;
		public int m_agariScore;
	}

	public static class Score {
		public int m_oyaRon;
		public int m_oyaTsumo;
		public int m_koRon;
		public int m_koTsumo;

		public Score(Score a_score) {
			this.m_oyaRon = a_score.m_oyaRon;
			this.m_oyaTsumo = a_score.m_oyaTsumo;
			this.m_koRon = a_score.m_koRon;
			this.m_koTsumo = a_score.m_koTsumo;
		}

		public Score(int a_oyaRon, int a_oyaTsumo, int a_koRon, int a_koTsumo) {
			this.m_oyaRon = a_oyaRon;
			this.m_oyaTsumo = a_oyaTsumo;
			this.m_koRon = a_koRon;
			this.m_koTsumo = a_koTsumo;
		}
	}

	Score m_scoreWork;
	final static Score[][] SCORE = {
			{new Score(    0,    0,    0,    0),new Score(    0,    0,    0,    0),new Score( 1500,  500, 1000,  300),new Score( 2000,  700, 1300,  400),new Score( 2400,  800, 1600,  400),new Score( 2900, 1000, 2000,  500),new Score( 3400, 1200, 2300,  600),new Score( 3900, 1300, 2600,  700),new Score( 4400, 1500, 2900,  800),new Score( 4800, 1600, 3200,  800),new Score( 5300,    0, 3600,    0),new Score( 5800,    0, 3900,    0),new Score( 6300,    0, 2100,    0)},
			{new Score( 2000,  700, 1300,  400),new Score( 2400,    0, 1600,    0),new Score( 2900, 1000, 2000,  500),new Score( 3900, 1300, 2600,  700),new Score( 4800, 1600, 3200,  800),new Score( 5800, 2000, 3900, 1000),new Score( 6800, 2300, 4500, 1200),new Score( 7700, 2600, 5200, 1300),new Score( 8700, 2900, 5800, 1500),new Score( 9600, 3200, 6400, 1600),new Score(10600, 3600, 7100, 1800),new Score(11600, 3900, 7700, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score( 3900, 1300, 2600,  700),new Score( 4800, 1600, 3200,  800),new Score( 5800, 2000, 3900, 1000),new Score( 7700, 2600, 5200, 1300),new Score( 9600, 3200, 6400, 1600),new Score(11600, 3900, 7700, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score( 7700, 2600, 5200, 1300),new Score( 9600, 3200, 6400, 1600),new Score(11600, 3900, 7700, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000)},
			{new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000)},
			{new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000)},
			{new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000)},
			{new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000)},
			{new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000)},
			{new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000)},
			{new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000)}};

	/** ÉJÉEÉìÉgÉtÉHÅ[É}ÉbÉg */
	private CountFormat countFormat = new CountFormat();

	/**
	 * ïÑÇåvéZÇµÇ‹Ç∑ÅB
	 *
	 * @param tehai
	 *            éËîv addHai òaóπÇ¡ÇΩîv combi éËîvÇÃëgÇ›çáÇÌÇπÅ@
	 *
	 * @return int ïÑ
	 *
	 */
	int countHu(Tehai tehai, Hai addHai, Combi combi, Yaku yaku,AgariSetting setting) {
		int countHu = 20;
		Hai checkHais[];

		//éµëŒéqÇÃèÍçáÇÕÇQÇTïÑ
//		if(yaku.checkTeetoitu() == true){
//			return 25;
//		}

		//ì™ÇÃîvÇéÊìæ
		Hai atamaHai = new Hai(Hai.noKindToId(combi.m_atamaNoKind));

		// ÇRå≥îvÇ»ÇÁÇQïÑí«â¡
		if (atamaHai.getKind() == KIND_SANGEN) {
			countHu += 2;
		}

		// èÍïóÇ»ÇÁÇQïÑí«â¡
		if ((atamaHai.getId() - ID_TON) == setting.getBakaze()){
			countHu += 2;
		}

		// é©ïóÇ»ÇÁÇQïÑí«â¡
		if ((atamaHai.getId() - ID_TON) == setting.getJikaze()){
			countHu += 2;
		}

		//ïΩòaÇ™ê¨óßÇ∑ÇÈèÍçáÇÕÅAë“ÇøÇ…ÇÊÇÈÇQïÑí«â¡ÇÊÇËÇ‡óDêÊÇ≥ÇÍÇÈ
		if(yaku.checkPinfu() == false){
			// íPãRë“ÇøÇÃèÍçáÇQïÑí«â¡
			if(addHai.getNoKind() == combi.m_atamaNoKind){
				countHu += 2;
			}

			// õ∆í£ë“ÇøÇÃèÍçáÇQïÑí«â¡
			//êîîvÇÃÇQÅ`ÇWÇ©Ç«Ç§Ç©îªíË
			if(addHai.isYaochuu() == false){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if((addHai.getNoKind()-1) == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}

			// ï”í£ë“Çø(3)ÇÃèÍçáÇQïÑí«â¡
			if((addHai.isYaochuu() == false) && (addHai.getNo() == NO_3)){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if( (addHai.getNoKind()-2) == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}

			// ï”í£ë“Çø(7)ÇÃèÍçáÇQïÑí«â¡
			if((addHai.isYaochuu() == false) && (addHai.getNo() == NO_7)){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if( addHai.getNoKind() == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}
		}

		// à√çèÇ…ÇÊÇÈâ¡ì_
		for (int i = 0; i < combi.m_kouNum; i++) {
			Hai checkHai = new Hai(Hai.noKindToId(combi.m_kouNoKinds[i]));
			// îvÇ™éöîvÇ‡ÇµÇ≠ÇÕ1,9
			if (checkHai.isYaochuu() == true) {
				countHu += 8;
			} else {
				countHu += 4;
			}
		}

		Fuuro[] fuuros;
		fuuros = tehai.getFuuros();
		int fuuroNum;
		fuuroNum = tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
				//ñæçèÇÃîvÇÉ`ÉFÉbÉN
				checkHais = fuuros[i].getHais();
				// îvÇ™éöîvÇ‡ÇµÇ≠ÇÕ1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 4;
				} else {
					countHu += 2;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//ñæû»ÇÃîvÇÉ`ÉFÉbÉN
				checkHais = fuuros[i].getHais();
				// îvÇ™éöîvÇ‡ÇµÇ≠ÇÕ1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 16;
				} else {
					countHu += 8;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//à√û»ÇÃîvÇÉ`ÉFÉbÉN
				checkHais = fuuros[i].getHais();
				// îvÇ™éöîvÇ‡ÇµÇ≠ÇÕ1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 32;
				} else {
					countHu += 16;
				}
				break;
			default:
				break;
			}
		}

		// ÉcÉÇè„Ç™ÇËÇ≈ïΩòaÇ™ê¨óßÇµÇƒÇ¢Ç»ÇØÇÍÇŒÇQïàí«â¡
		if(setting.getYakuflg(AgariSetting.YakuflgName.TUMO.ordinal() )== true){
			if(yaku.checkPinfu() == false){
				countHu += 2;
			}
		}

		// ñ ëOÉçÉìè„Ç™ÇËÇÃèÍçáÇÕÇPÇOïÑí«â¡
		if(setting.getYakuflg(AgariSetting.YakuflgName.TUMO.ordinal() )== false){
			if (yaku.nakiflg == false) {
				countHu += 10;
			}
		}

		// àÍÇÃà Ç™Ç†ÇÈèÍçáÇÕÅAêÿÇËè„Ç∞
		if (countHu % 10 != 0) {
			countHu = countHu - (countHu % 10) + 10;
		}

		return countHu;
	}

	/**
	 * è„Ç™ÇËì_êîÇéÊìæÇµÇ‹Ç∑ÅB
	 *
	 * @param m_tehai
	 *            éËîv addHai òaóπÇ¡ÇΩîv combi éËîvÇÃëgÇ›çáÇÌÇπÅ@
	 *
	 * @return int òaóπÇËì_
	 */
	public int getScore(int hanSuu, int huSuu) {
		if (hanSuu == 0) {
			return 0;
		}

		int iFu;
		if (huSuu == 20) {
			iFu = 0;
		} else if (huSuu == 25) {
			iFu = 1;
		} else if (huSuu > 120) {
			iFu = 12;
		} else {
			iFu = (huSuu / 10) - 1;
		}

		int iHan;
		if (hanSuu > 13) {
			iHan = 13 - 1;
		} else {
			iHan = hanSuu - 1;
		}

		m_scoreWork = new Score(SCORE[iHan][iFu]);

		return m_scoreWork.m_koRon;

//		int score;
//		// ïÑÅ@Å~ ÇQÇÃÅ@Åiñ|êîÅ@+Å@èÍÉ]ÉçÇÃ2ñ|)èÊ
//		score = huSuu * (int) Math.pow(2, hanSuu + 2);
//		// éqÇÕè„ÇÃ4î{Ç™äÓñ{ì_(êeÇÕ6î{)
//		score *= 4;
//
//		// 100Ç≈äÑÇËêÿÇÍÇ»Ç¢êîÇ™Ç†ÇÈèÍçá100ì_åJè„Ç∞
//		if (score % 100 != 0) {
//			score = score - (score % 100) + 100;
//		}
//		// 7700à»è„ÇÕ8000Ç∆Ç∑ÇÈ
////		if (score >= 7700) {
////			score = 8000;
////		}
//
//		if (hanSuu >= 13) { // 13ñ|à»è„ÇÕññû
//			score = 32000;
//		} else if (hanSuu >= 11) { // 11ñ|à»è„ÇÕ3î{ñû
//			score = 24000;
//		} else if (hanSuu >= 8) { // 8ñ|à»è„ÇÕî{ñû
//			score = 16000;
//		} else if (hanSuu >= 6) { // 6ñ|à»è„ÇÕíµñû
//			score = 12000;
//		} else if (hanSuu >= 5) { // 5ñ|à»è„ÇÕñûä—
//			score = 8000;
//		}
//
//		return score;
	}

	public int getAgariScore(Tehai tehai, Hai addHai, Combi[] combis,AgariSetting setting, AgariInfo a_agariInfo) {
		// ÉJÉEÉìÉgÉtÉHÅ[É}ÉbÉgÇéÊìæÇµÇ‹Ç∑ÅB
		countFormat.setCountFormat(tehai, addHai);

		// Ç†Ç™ÇËÇÃëgÇ›çáÇÌÇπÇéÊìæÇµÇ‹Ç∑ÅB
		int combisCount = countFormat.getCombis(combis);
		combis = countFormat.getCombis();
		combisCount = countFormat.getCombiNum();

		if (countFormat.isChiitoitsu()) {
			Yaku yaku = new Yaku(tehai, addHai, combis[0], setting, 0);
			String[] yakuNames = yaku.getYakuName();
			int hanSuu = yaku.getHan();
			int agariScore = getScore(hanSuu, 25);
			a_agariInfo.m_score = m_scoreWork;
			a_agariInfo.m_fu = 25;
			a_agariInfo.m_han = hanSuu;
			a_agariInfo.m_yakuNames = yakuNames;

			return agariScore;
		}

		// Ç†Ç™ÇËÇÃëgÇ›çáÇÌÇπÇ™Ç»Ç¢èÍçáÇÕ0ì_
		if (combisCount == 0) {
			Yaku yaku = new Yaku(tehai, addHai, setting);
			String[] yakuNames = yaku.getYakuName();
			for (int i = 0; i < yakuNames.length; i++) {
				if (yakuNames[i].equals("çëémñ≥ëo 13ñ|")) {
					int hanSuu = 13;
					int agariScore = getScore(hanSuu, 20);
					a_agariInfo.m_score = m_scoreWork;
					a_agariInfo.m_fu = 0;
					a_agariInfo.m_han = hanSuu;
					a_agariInfo.m_yakuNames = yakuNames;

					return agariScore;
				}
			}
			return 0;
		}

		// ñ
		int hanSuu[] = new int[combisCount];
		// ïÑ
		int huSuu[] = new int[combisCount];
		// ì_êîÅiéqÇÃÉçÉìè„Ç™ÇËÅj
		int agariScore[] = new int[combisCount];
		// ç≈ëÂÇÃì_êî
		int maxagariScore = 0;

		for (int i = 0; i < combisCount; i++) {
			Yaku yaku = new Yaku(tehai, addHai, combis[i], setting);
			hanSuu[i] = yaku.getHanSuu();
			huSuu[i] = countHu(tehai, addHai, combis[i],yaku,setting);
			agariScore[i] = getScore(hanSuu[i], huSuu[i]);

			if(maxagariScore < agariScore[i]){
				a_agariInfo.m_score = m_scoreWork;
				maxagariScore = agariScore[i];
				a_agariInfo.m_fu = huSuu[i];
				a_agariInfo.m_han = hanSuu[i];
				a_agariInfo.m_yakuNames = yaku.getYakuName();
			}
		}

		// ç≈ëÂílÇíTÇ∑
		maxagariScore = agariScore[0];
		for (int i = 0; i < combisCount; i++) {
			maxagariScore = Math.max(maxagariScore, agariScore[i]);
		}
		return maxagariScore;
	}

	public String[] getYakuName(Tehai tehai, Hai addHai, Combi[] combis,AgariSetting setting) {
		//òaóπÇËñÇÃñºëO
		String[] yakuNames = {""};
		// ÉJÉEÉìÉgÉtÉHÅ[É}ÉbÉgÇéÊìæÇµÇ‹Ç∑ÅB
		countFormat.setCountFormat(tehai, addHai);

		// Ç†Ç™ÇËÇÃëgÇ›çáÇÌÇπÇéÊìæÇµÇ‹Ç∑ÅB
		int combisCount = countFormat.getCombis(combis);

		// Ç†Ç™ÇËÇÃëgÇ›çáÇÌÇπÇ™Ç»Ç¢èÍçáÇÕ0ì_
		if (combisCount == 0){
			return yakuNames;
		}

		// ñ
		int hanSuu[] = new int[combisCount];
		// ïÑ
		int huSuu[] = new int[combisCount];
		// ì_êîÅiéqÇÃÉçÉìè„Ç™ÇËÅj
		int agariScore[] = new int[combisCount];
		// ç≈ëÂÇÃì_êî
		int maxagariScore = 0;


		for (int i = 0; i < combisCount; i++) {
			Yaku yaku = new Yaku(tehai, addHai, combis[i], setting);
			hanSuu[i] = yaku.getHanSuu();
			huSuu[i] = countHu(tehai, addHai, combis[i],yaku,setting);
			agariScore[i] = getScore(hanSuu[i], huSuu[i]);

			if(maxagariScore < agariScore[i]){
				maxagariScore = agariScore[i];
				yakuNames = yaku.getYakuName();
			}
		}

		return yakuNames;
	}
}
