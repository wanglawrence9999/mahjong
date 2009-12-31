package jp.sourceforge.andjong.mahjong;

import static jp.sourceforge.andjong.mahjong.Hai.*;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

public class AgariScore {
	/** ƒJƒEƒ“ƒgƒtƒH[ƒ}ƒbƒg */
	private CountFormat countFormat = new CountFormat();

	/**
	 * •„‚ğŒvZ‚µ‚Ü‚·B
	 *
	 * @param tehai
	 *            è”v addHai ˜a—¹‚Á‚½”v combi è”v‚Ì‘g‚İ‡‚í‚¹@
	 *
	 * @return int •„
	 *
	 */
	int countHu(Tehai tehai, Hai addHai, Combi combi, Yaku yaku,AgariSetting setting) {
		int countHu = 20;
		Hai checkHais[];

		//µ‘Îq‚Ìê‡‚Í‚Q‚T•„
		if(yaku.checkTeetoitu() == true){
			return 25;
		}

		//“ª‚Ì”v‚ğæ“¾
		Hai atamaHai = new Hai(Hai.noKindToId(combi.m_atamaNoKind));

		// ‚RŒ³”v‚È‚ç‚Q•„’Ç‰Á
		if (atamaHai.getKind() == KIND_SANGEN) {
			countHu += 2;
		}

		// ê•—‚È‚ç‚Q•„’Ç‰Á
		if (atamaHai.getId() == setting.getBakaze()){
			countHu += 2;
		}

		// ©•—‚È‚ç‚Q•„’Ç‰Á
		if (atamaHai.getId() == setting.getJikaze()){
			countHu += 2;
		}

		//•½˜a‚ª¬—§‚·‚éê‡‚ÍA‘Ò‚¿‚É‚æ‚é‚Q•„’Ç‰Á‚æ‚è‚à—Dæ‚³‚ê‚é
		if(yaku.checkPinfu() == false){
			// ’P‹R‘Ò‚¿‚Ìê‡‚Q•„’Ç‰Á
			if(addHai.getNoKind() == combi.m_atamaNoKind){
				countHu += 2;
			}

			// ›Æ’£‘Ò‚¿‚Ìê‡‚Q•„’Ç‰Á
			//””v‚Ì‚Q`‚W‚©‚Ç‚¤‚©”»’è
			if(addHai.isYaochuu() == false){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if((addHai.getNoKind()-1) == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}

			// •Ó’£‘Ò‚¿(3)‚Ìê‡‚Q•„’Ç‰Á
			if((addHai.isYaochuu() == false) && (addHai.getNo() == NO_3)){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if( (addHai.getNoKind()-2) == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}

			// •Ó’£‘Ò‚¿(7)‚Ìê‡‚Q•„’Ç‰Á
			if((addHai.isYaochuu() == false) && (addHai.getNo() == NO_7)){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if( addHai.getNoKind() == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}
		}

		// ˆÃ‚É‚æ‚é‰Á“_
		for (int i = 0; i < combi.m_kouNum; i++) {
			Hai checkHai = new Hai(Hai.noKindToId(combi.m_kouNoKinds[i]));
			// ”v‚ªš”v‚à‚µ‚­‚Í1,9
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
				//–¾‚Ì”v‚ğƒ`ƒFƒbƒN
				checkHais = fuuros[i].getHais();
				// ”v‚ªš”v‚à‚µ‚­‚Í1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 4;
				} else {
					countHu += 2;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//–¾È‚Ì”v‚ğƒ`ƒFƒbƒN
				checkHais = fuuros[i].getHais();
				// ”v‚ªš”v‚à‚µ‚­‚Í1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 16;
				} else {
					countHu += 8;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//ˆÃÈ‚Ì”v‚ğƒ`ƒFƒbƒN
				checkHais = fuuros[i].getHais();
				// ”v‚ªš”v‚à‚µ‚­‚Í1,9
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

		// ƒcƒ‚ã‚ª‚è‚Å•½˜a‚ª¬—§‚µ‚Ä‚¢‚È‚¯‚ê‚Î‚Q•ˆ’Ç‰Á
		if(setting.getYakuflg(AgariSetting.YakuflgName.TUMO.ordinal() )== true){
			if(yaku.checkPinfu() == false){
				countHu += 2;
			}
		}

		// –Ê‘Oƒƒ“ã‚ª‚è‚Ìê‡‚Í‚P‚O•„’Ç‰Á
		if(setting.getYakuflg(AgariSetting.YakuflgName.TUMO.ordinal() )== false){
			if (yaku.nakiflg == false) {
				countHu += 10;
			}
		}

		// ˆê‚ÌˆÊ‚ª‚ ‚éê‡‚ÍAØ‚èã‚°
		if (countHu % 10 != 0) {
			countHu = countHu - (countHu % 10) + 10;
		}

		return countHu;
	}

	/**
	 * ã‚ª‚è“_”‚ğæ“¾‚µ‚Ü‚·B
	 *
	 * @param tehai
	 *            è”v addHai ˜a—¹‚Á‚½”v combi è”v‚Ì‘g‚İ‡‚í‚¹@
	 *
	 * @return int ˜a—¹‚è“_
	 */
	public int getScore(int hanSuu, int huSuu) {
		int score;
		// •„@~ ‚Q‚Ì@i–|”@+@êƒ]ƒ‚Ì2–|)æ
		score = huSuu * (int) Math.pow(2, hanSuu + 2);
		// q‚Íã‚Ì4”{‚ªŠî–{“_(e‚Í6”{)
		score *= 4;

		// 100‚ÅŠ„‚èØ‚ê‚È‚¢”‚ª‚ ‚éê‡100“_ŒJã‚°
		if (score % 100 != 0) {
			score = score - (score % 100) + 100;
		}
		// 7700ˆÈã‚Í8000‚Æ‚·‚é
		if (score >= 7700) {
			score = 8000;
		}

		if (hanSuu >= 13) { // 13–|ˆÈã‚Í–ğ–
			score = 32000;
		} else if (hanSuu >= 11) { // 11–|ˆÈã‚Í3”{–
			score = 24000;
		} else if (hanSuu >= 8) { // 8–|ˆÈã‚Í”{–
			score = 16000;
		} else if (hanSuu >= 6) { // 6–|ˆÈã‚Í’µ–
			score = 12000;
		} else if (hanSuu >= 5) { // 5–|ˆÈã‚Í–ŠÑ
			score = 8000;
		}

		return score;
	}

	/** ˜a—¹‚è–ğ‚Ì–¼‘O */
	String[] m_yakuNames;

	public String[] getYakuNames() {
		return m_yakuNames;
	}

	public int getAgariScore(Tehai tehai, Hai addHai, Combi[] combis,AgariSetting setting) {
		// ƒJƒEƒ“ƒgƒtƒH[ƒ}ƒbƒg‚ğæ“¾‚µ‚Ü‚·B
		countFormat.setCountFormat(tehai, addHai);

		// ‚ ‚ª‚è‚Ì‘g‚İ‡‚í‚¹‚ğæ“¾‚µ‚Ü‚·B
		int combisCount = countFormat.getCombis(combis);
		combis = countFormat.getCombis();
		combisCount = countFormat.getCombiNum();

		// ‚ ‚ª‚è‚Ì‘g‚İ‡‚í‚¹‚ª‚È‚¢ê‡‚Í0“_
		if (combisCount == 0)
			return 0;

		// –ğ
		int hanSuu[] = new int[combisCount];
		// •„
		int huSuu[] = new int[combisCount];
		// “_”iq‚Ìƒƒ“ã‚ª‚èj
		int agariScore[] = new int[combisCount];
		// Å‘å‚Ì“_”
		int maxagariScore = 0;

		for (int i = 0; i < combisCount; i++) {
			Yaku yaku = new Yaku(tehai, addHai, combis[i], setting);
			hanSuu[i] = yaku.getHanSuu();
			huSuu[i] = countHu(tehai, addHai, combis[i],yaku,setting);
			agariScore[i] = getScore(hanSuu[i], huSuu[i]);

			if(maxagariScore < agariScore[i]){
				maxagariScore = agariScore[i];
				m_yakuNames = yaku.getYakuName();
			}
		}

		// Å‘å’l‚ğ’T‚·
		maxagariScore = agariScore[0];
		for (int i = 0; i < combisCount; i++) {
			maxagariScore = Math.max(maxagariScore, agariScore[i]);
		}
		return maxagariScore;
	}

	public String[] getYakuName(Tehai tehai, Hai addHai, Combi[] combis,AgariSetting setting) {
		//˜a—¹‚è–ğ‚Ì–¼‘O
		String[] yakuNames = {""};
		// ƒJƒEƒ“ƒgƒtƒH[ƒ}ƒbƒg‚ğæ“¾‚µ‚Ü‚·B
		countFormat.setCountFormat(tehai, addHai);

		// ‚ ‚ª‚è‚Ì‘g‚İ‡‚í‚¹‚ğæ“¾‚µ‚Ü‚·B
		int combisCount = countFormat.getCombis(combis);

		// ‚ ‚ª‚è‚Ì‘g‚İ‡‚í‚¹‚ª‚È‚¢ê‡‚Í0“_
		if (combisCount == 0){
			return yakuNames;
		}

		// –ğ
		int hanSuu[] = new int[combisCount];
		// •„
		int huSuu[] = new int[combisCount];
		// “_”iq‚Ìƒƒ“ã‚ª‚èj
		int agariScore[] = new int[combisCount];
		// Å‘å‚Ì“_”
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
