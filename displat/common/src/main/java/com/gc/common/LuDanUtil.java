package com.gc.common;

/**
 *@author gavin
 *@since 2018年12月3日 下午2:58:46
 *@description 路单工具
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LuDanUtil {

	/**
	 * 生成大路图
	 * 
	 * @param gameResults 游戏开奖结果 1 红/龙 2 黑/虎 0 和
	 * @rows 横向有多少列
	 * @cols 纵向有多少行
	 * @return
	 */
	public static List<Object[]> createDaLuTu(List<Integer> gameResults, int rows, int cols) {
		if (gameResults == null || gameResults.size() < 1) {
			return null;
		}
		// 初始化数组,按列存
		List<Object[]> resList = new ArrayList<>();
		Object[] col = null;
		int des = 0;
		int c = 0;// 当前列
		int h = 0;// 和次数
		for (int i = 0; i < gameResults.size(); i++) {
			if (i == 0) {
				col = wrapCol(cols);
				if (gameResults.get(i).equals(0)) {
					col[0] = new Object[] { 0, 1 };
					h++;
					if (i < gameResults.size() - 1) {
						while (gameResults.get(i + 1) == 0) {
							i++;
							h++;
							col[0] = new Object[] { 0, h };
						}
					}
				} else {
					col[0] = gameResults.get(0);
				}
				resList.add(col);
			} else {
				if (gameResults.get(i - 1).equals(gameResults.get(i))||(gameResults.get(i-1)==0&&i-1-h>=0&&gameResults.get(i-1-h)==gameResults.get(i))) {

					// 和
					if (gameResults.get(i).equals(0)) {
						h++;
						Object[] tempObj = (Object[]) col[des];
						tempObj[1] = h;
						col[des] = tempObj;
					} else {
						des++;
						if (des >= cols) {
							col = wrapCol(cols);
							resList.add(col);
							c++;
							des = des-1;
						} else {
							if (!col[des].equals(0)) {
								if (c == resList.size() - 1) {
									col = wrapCol(cols);
									resList.add(col);
									c++;
								} else {
									c++;
									col = resList.get(c);
								}
								des--;
							}
						}
						col[des] = gameResults.get(i);
					}

				} else {
					// 当前是和
					if (gameResults.get(i) == 0) {
						h++;
						if (col[des].equals(1)) {
							col[des] = new Object[] { 3, h };
						} else {
							col[des] = new Object[] { 4, h };
						}
					} else {
						h = 0;
						int temp = getCol(resList, c);
						if (temp == -1) {
							col = wrapCol(cols);
							resList.add(col);
							c++;
						} else {
							c = temp;
						}
						col = resList.get(c);
						des = 0;
						col[des] = gameResults.get(i);
					}
				}
			}

		}
		
		if(resList.size()>rows) {
			return resList.subList(resList.size()-rows, resList.size()-1);
		}
		return resList;
	}
	
	public static List<Object[]> createDaYanLuTu(List<Object[]> list){
		if(list==null||list.size()<2) {
		    return null;
		}
		List<Object[]> resList=new ArrayList<>();
		int col=1;
		int rows=1;
		int des = -1;
		int c=0;
		int last =0;
		int temp=0;
		//从第二列第二个开始
		Object[] objs = wrapCol(list.get(1).length);
		resList.add(objs);
		for(int i=col;i<list.size();i++) {
			for(int j=rows;j<list.get(i).length;j++) {
				if(list.get(i)[j]==null||list.get(i)[j].equals(0)) {
					break;
				}
				if(j==0) {
					if(compCols(list.get(i-1),list.get(i-2))) {
						temp=1;
					}else {
						temp=2;
					}
				}else {
					//有无
					if(!list.get(i-1)[j].equals(0)) {
						temp=1;
					}else {
						temp=2;
					}
				}
				if(last!=0&&last!=temp) {
					int t = getCol(resList, c);
					if(t==-1) {
					  objs = wrapCol(list.get(i).length);
					  c++;
					  resList.add(objs);
					}else {
						c=t;
						objs=resList.get(c);
					}
					 des=0;
				}else {
					if(des+1>=objs.length||!objs[des+1].equals(0)) {
						if(c==resList.size()-1) {
							objs = wrapCol(list.get(i).length);
							resList.add(objs);
						}
						c++;
					}else {
						des++;
					}
				}
				last=temp;
				objs[des]=temp;
			}
			rows=0;
		}
		
		return resList;
		
	}
	
	public static List<Object[]> createXiaoYanLuTu(List<Object[]> list){
		if(list==null||list.size()<3) {
		    return null;
		}
		List<Object[]> resList=new ArrayList<>();
		int col=2;
		int rows=1;
		int des = -1;
		int c=0;
		int last =0;
		int temp=0;
		//从第三列第二个开始
		Object[] objs = wrapCol(list.get(1).length);
		resList.add(objs);
		for(int i=col;i<list.size();i++) {
			for(int j=rows;j<list.get(i).length;j++) {
				if(list.get(i)[j]==null||list.get(i)[j].equals(0)) {
					break;
				}
				if(j==0) {
					if(compCols(list.get(i),list.get(i-2))) {
						temp=1;
					}else {
						temp=2;
					}
				}else {
					//有无
					if(!list.get(i-2)[j].equals(0)) {
						temp=1;
					}else {
						temp=2;
					}
				}
				if(last!=0&&last!=temp) {
					int t = getCol(resList, c);
					if(t==-1) {
					  objs = wrapCol(list.get(i).length);
					  c++;
					  resList.add(objs);
					}else {
						c=t;
						objs=resList.get(c);
					}
					 des=0;
				}else {
					if(des+1>=objs.length||!objs[des+1].equals(0)) {
						if(c==resList.size()-1) {
							objs = wrapCol(list.get(i).length);
							resList.add(objs);
						}
						c++;
					}else {
						des++;
					}
				}
				last=temp;
				objs[des]=temp;
			}
			rows=0;
		}
		
		return resList;
		
	}
	
	public static List<Object[]> createYueYouLuTu(List<Object[]> list){
		if(list==null||list.size()<4) {
		    return null;
		}
		List<Object[]> resList=new ArrayList<>();
		int col=3;
		int rows=1;
		int des = -1;
		int c=0;
		int last =0;
		int temp=0;
		//从第三列第二个开始
		Object[] objs = wrapCol(list.get(1).length);
		resList.add(objs);
		for(int i=col;i<list.size();i++) {
			for(int j=rows;j<list.get(i).length;j++) {
				if(list.get(i)[j]==null||list.get(i)[j].equals(0)) {
					break;
				}
				if(j==0) {
					if(compCols(list.get(i),list.get(i-3))) {
						temp=1;
					}else {
						temp=2;
					}
				}else {
					//有无
					if(!list.get(i-3)[j].equals(0)) {
						temp=1;
					}else {
						temp=2;
					}
				}
				if(last!=0&&last!=temp) {
					int t = getCol(resList, c);
					if(t==-1) {
					  objs = wrapCol(list.get(i).length);
					  c++;
					  resList.add(objs);
					}else {
						c=t;
						objs=resList.get(c);
					}
					 des=0;
				}else {
					if(des+1>=objs.length||!objs[des+1].equals(0)) {
						if(c==resList.size()-1) {
							objs = wrapCol(list.get(i).length);
							resList.add(objs);
						}
						c++;
					}else {
						des++;
					}
				}
				last=temp;
				objs[des]=temp;
			}
			rows=0;
		}
		
		return resList;
		
	}
	
	/**
	 * 判断两列是否齐整
	 * @param col1
	 * @param col2
	 * @return
	 */
	private static boolean compCols(Object[] col1,Object[] col2) {
		long count1 = Arrays.stream(col1).filter(x->x.equals(0)).count();
		long count2 = Arrays.stream(col2).filter(x->x.equals(0)).count();
		if(count1==count2) {
			return true;
		}
		return false;
	}

	private static Object[] wrapCol(int cols) {
		Object[] a = new Object[cols];
		for (int i = 0; i < cols; i++) {
			a[i] = 0;
		}
		return a;
	}

	private static int getCol(List<Object[]> list, int col) {
		for (int i = col; i >= 0; i--) {
			if (!list.get(i)[0].equals(0)) {
				return (i + 1) == list.size() ? -1 : (i + 1);
			}
		}
		return -1;
	}


}
