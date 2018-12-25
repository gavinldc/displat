package com.gc.common;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @description 红黑大战游戏牌面存储和计算方案
 * @author gavin.lyu
 * @since 2018-11-19 17:48
 */
public class CardScheme {

	/**
	 * 黑桃2-A
	 */
	private static final List<Integer> SPADES_CARD = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
	/**
	 * 红桃2-A
	 */
	private static final List<Integer> RED_PEACH_CARD = Arrays.asList(15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
			27);
	/**
	 * 梅花2-A
	 */
	private static final List<Integer> PLUM_CARD = Arrays.asList(28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40);
	/**
	 * 方片2-A
	 */
	private static final List<Integer> BLOCK_CARD = Arrays.asList(41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53);

	/**
	 * 特殊牌面
	 */
	public static final int[] ZJH_SMALLEST = new int[] { 2, 3, 5 };

	/**
	 * 牌面花色定义，和数据库存储值保持一致
	 */
	public static class CARD_COLOR {
		// 黑桃
		public final static int SPADES = 3;
		// 红桃
		public final static int RED_PEACH = 2;
		// 梅花
		public final static int PLUM = 1;
		// 方片
		public final static int BLOCK = 0;
	}

	/**
	 * 牌面类型 黑方牌型 0 特殊1 单张 2 对子 3 顺子 4 金花 5 顺金 6 豹子
	 */
	public static class CARD_TYPE {
		/**
		 * 特殊牌面 235
		 */
		public final static int SPECIAL = 0;
		/**
		 * 单张
		 */
		public final static int SINGLE = 1;
		/**
		 * 对子
		 */
		public final static int SAME_TWO = 2;
		/**
		 * 顺子
		 */
		public final static int DIF_COLOR_TOGETHER = 3;
		/**
		 * 金花
		 */
		public final static int SAME_COLOR_UNTOGETHER = 4;
		/**
		 * 顺金
		 */
		public final static int SAME_COLOR_TOGETHER = 5;
		/**
		 * 豹子
		 */
		public final static int SAME_THREE = 6;
	}

	private static final List<Integer> WHOLE_CARD = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43,
			44, 45, 46, 47, 48, 49, 50, 51, 52, 53);

	public static final Integer CARD_SIZE = WHOLE_CARD.size();

	/**
	 * 获取所有牌
	 * 
	 * @return
	 */
	public static List<Integer> getWholeCards() {
		return WHOLE_CARD;
	}

	/**
	 * 随机洗牌，按照随机插入的方式
	 * 
	 * @return
	 */
	public static List<Integer> shuffleCards() {
		List<Integer> tempList = new ArrayList<Integer>(WHOLE_CARD);
		Collections.copy(tempList, WHOLE_CARD);
		Collections.shuffle(tempList);
		return tempList;
	}

	/**
	 * 获取牌面的花色
	 * 
	 * @param code 牌面值
	 * @return
	 */
	public static int getCardColor(int code) {
		if (SPADES_CARD.contains(code)) {
			return CARD_COLOR.SPADES;
		}
		if (RED_PEACH_CARD.contains(code)) {
			return CARD_COLOR.RED_PEACH;
		}
		if (BLOCK_CARD.contains(code)) {
			return CARD_COLOR.PLUM;
		}
		if (PLUM_CARD.contains(code)) {
			return CARD_COLOR.BLOCK;
		}
		LogicError le = Errors.clone(BizErrors.E2002, "位置牌面");
		throw le;
	}

	/**
	 * 获取牌类型
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static int getCardType(int a, int b, int c) {

		int aColor = getCardColor(a);
		int bColor = getCardColor(b);
		int cColor = getCardColor(c);

		int[] ints = getOriginal(new int[] { a, b, c });
		Arrays.sort(ints);
		// 花色相同
		if (aColor == bColor && bColor == cColor) {
			if ((ints[2] == ints[1] + 1) && (ints[1] == ints[0] + 1)) {
				return CARD_TYPE.SAME_COLOR_TOGETHER;// 顺金
			} else {
				return CARD_TYPE.SAME_COLOR_UNTOGETHER;// 金花
			}
		}
		// 花色不相同
		if ((ints[2] == ints[1] + 1) && (ints[1] == ints[0] + 1)) {
			return CARD_TYPE.DIF_COLOR_TOGETHER;// 顺子
		}

		if (ints[0] == ints[1] && ints[1] == ints[2]) {
			return CARD_TYPE.SAME_THREE; // 豹子
		}
		if (ints[0] == ints[1] || ints[1] == ints[2]) {
			return CARD_TYPE.SAME_TWO;// 对子
		}
		if (ints[0] == 2 && ints[1] == 3 && ints[2] == 5) {
			if(aColor!=bColor&&aColor!=cColor&&bColor!=cColor) {
				return CARD_TYPE.SPECIAL;//特殊牌型
			}
		}
		return CARD_TYPE.SINGLE;
	}

	/**
	 * 获取牌型中文名
	 * 
	 * @param cardType
	 * @return
	 */
	public static String getCardTypeName(int cardType) {
		switch (cardType) {
		case 1:
			return "单牌";
		case 2:
			return "对子";
		case 3:
			return "顺子";
		case 4:
			return "金花";
		case 5:
			return "顺金";
		case 6:
			return "豹子";

		default:
			return "";
		}
	}

	/**
	 * 获取牌面的原始数值并按数值升序排列
	 * 
	 * @param cards 牌面数组
	 * @return
	 */
	public static int[] getOriginal(int[] cards) {
		int[] result = new int[cards.length];
		for (int i = 0; i < cards.length; i++) {
			int num = genOriginalNum(cards[i]);
			result[i] = num;
		}
		Arrays.sort(result);
		return result;
	}

	/**
	 * 获取牌面的原始数值
	 * 
	 * @param card 牌面
	 * @return
	 */
	private static int genOriginalNum(int card) {
		int num = card % 13;
		num = num == 0 ? 13 : num;
		num = num == 1 ? 13 + 1 : num;
		return num;
	}

	/**
	 * 比牌
	 * 
	 * @param cards1
	 * @param cards2
	 * @return
	 */
	public static int compareCard(int[] cards1, int[] cards2) {

		int type1 = getCardType(cards1[0], cards1[1], cards1[2]);
		int type2 = getCardType(cards2[0], cards2[1], cards2[2]);
		int[] originalCards1 = getOriginal(cards1);
		int[] originalCards2 = getOriginal(cards2);
		int res = 0;
		// 牌型不同直接比
		if (type1 != type2) {
			//特殊牌型
			if(CARD_TYPE.SPECIAL==type1&&CARD_TYPE.SAME_THREE==type2&&originalCards2[2]==14) {
				return 1;
			}
			if(CARD_TYPE.SPECIAL==type2&&CARD_TYPE.SAME_THREE==type1&&originalCards1[2]==14) {
				return -1;
			}
			res = type1 - type2;
		} else {
			switch (type1) {
			case CARD_TYPE.SAME_THREE:
				// 花色
				int color1 = getCardColor(cards1[0]);
				int color2 = getCardColor(cards2[0]);
				res = originalCards1[0] + originalCards1[1] + originalCards1[2] - originalCards2[0] - originalCards2[1]
						- originalCards2[2];
				if (res == 0) {
					if (color1 == color2) {
						throw Errors.clone(BizErrors.E2002, String.format("比牌异常,%s,%s", cards1, cards2));

					} else {
						res = color1 - color2;
					}
				}
				break;
			case CARD_TYPE.SAME_COLOR_TOGETHER:
				// 顺金先比牌点
				res = originalCards1[0] + originalCards1[1] + originalCards1[2] - originalCards2[0] - originalCards2[1]
						- originalCards2[2];
				if (res == 0) {
					// 牌点相同再比花色
					int tcolor1 = getCardColor(cards1[0]);
					int tcolor2 = getCardColor(cards2[0]);
					res = tcolor1 - tcolor2;
				}
				break;
			case CARD_TYPE.SAME_COLOR_UNTOGETHER:
			case CARD_TYPE.DIF_COLOR_TOGETHER:
				// 先排序
				List<CardBean> tempCards1 = getOriginalCardBeans(cards1);
				List<CardBean> tempCards2 = getOriginalCardBeans(cards2);
				for (int i = 2; i >= 0; i--) {
					res = tempCards1.get(i).original - tempCards2.get(i).original;
					if (res != 0) {
						break;
					}
				}
				if (res == 0) {
					// 牌点相同再比花色
					int tcolor1 = tempCards1.get(2).color;
					int tcolor2 = tempCards2.get(2).color;
					res = tcolor1 - tcolor2;
				}
				break;
			case CARD_TYPE.SAME_TWO:
				// 获取原始牌
				int[] dCards1 = getOriginal(cards1);
				int[] dCards2 = getOriginal(cards2);
				// 先比对子牌的大小
				res = dCards1[1] - dCards2[1];
				if (res == 0) {
					// 比单牌
					int single1 = dCards1[0] == dCards1[1] ? dCards1[2] : dCards1[0];
					int single2 = dCards2[0] == dCards2[1] ? dCards2[2] : dCards2[0];
					res = single1 - single2;
					if (res == 0) {
						// 比对子花色
						int[] tempOCards1 = Arrays.copyOf(cards1, 3);
						Arrays.sort(tempOCards1);
						int toColor1 = getCardColor(tempOCards1[1]);
						int[] tempOCards2 = Arrays.copyOf(cards2, 3);
						Arrays.sort(tempOCards2);
						int toColor2 = getCardColor(tempOCards2[1]);
						res = toColor1 - toColor2;
						if (res == 0) {
							// 比单牌花色
							single1 = tempOCards1[1] == tempOCards1[0] ? tempOCards1[2] : tempOCards1[0];
							single2 = tempOCards2[1] == tempOCards2[0] ? tempOCards2[2] : tempOCards2[0];
							toColor1 = getCardColor(single1);
							toColor2 = getCardColor(single2);
							res = toColor1 - toColor2;
						}
					}
				}
				break;
			case CARD_TYPE.SINGLE:
				// 获取原始牌
				List<CardBean> sCards1 = getOriginalCardBeans(cards1);
				List<CardBean> sCards2 = getOriginalCardBeans(cards2);
				for (int i = 2; i >= 0; i--) {
					res = sCards1.get(i).original - sCards2.get(i).original;
					if (res != 0) {
						break;
					}
				}
				if (res == 0) {
					// 牌点相同再比花色
					for (int i = 2; i >= 0; i--) {
						res = sCards1.get(i).color - sCards2.get(i).color;
						if (res != 0) {
							break;
						}
					}
				}
			default:
				break;
			}
		}
		if (res == 0) {
			throw Errors.clone(BizErrors.E2002, String.format("比牌异常,%s,%s", cards1, cards2));
		}

		return res > 0 ? 1 : -1;
	}
	
	static class CardBean{
	    public int original;
		public int num;
		public int color;
		
		public static CardBean wrap(int cardNum) {
			CardBean bean = new CardBean();
			bean.original=genOriginalNum(cardNum);
			bean.num=cardNum;
			bean.color=getCardColor(cardNum);
			return bean;
		}
	}
	
	public static List<CardBean> getOriginalCardBeans(int[] cards) {
		List<CardBean> list = new ArrayList<>();
		for(int c:cards) {
		   list.add(CardBean.wrap(c));
		}
		list.sort(new Comparator<CardBean>() {

			@Override
			public int compare(CardBean o1, CardBean o2) {
				return o1.original-o2.original;
			}
		});
		return list;
	}

	public static void main(String[] msg) {
		System.out.println(compareCard(new int[] {18,37,10},new int[] {23,11,31}));
	}
}
