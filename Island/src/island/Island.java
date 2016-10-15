
package island;
import island.TraceList;
import island.IOHelper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Emelyanov Konstantin
 * @version 1.0
 * @since   2016-10-06
 */
public class Island {
    static int xN;
    static int xM;
    static int [][] m;
    static int [][] xNxM = new int[2][100]; 
    static int result = 0;
    static boolean [][] mB; // Массив просмотра элементов
    static int minBorder;
    static TraceList tList ;
    static int g= 0;
    public static void main(String[] args) {
        //**Input**//
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        byte IN = 1;		
	IN = IOHelper.readByte(reader);
	if (IN == 0) {
	    return;
	}
				
	ArrayList<int[][]> mArray = new ArrayList<>();  
        for (int inum = 0; inum < IN; inum++) {			
	    int[] v = IOHelper.readInt(reader, 2, 1, 50);
	    if (v == null) return;			
            xNxM[0][inum] = v[0];
            xNxM[1][inum] = v[1];
            m = new int[v[0]][v[1]];
            for (int r = 0; r < v[0]; r++) {
	        int[] row = IOHelper.readInt(reader, v[1], 1, 1000);
	        if (row == null)
		    return;
		else {
		    for (int c = 0; c < v[1]; c++) {
                        m[r][c]=row[c];	
			}
		    }
	    }
            mArray.add(inum, m);
        }    
        //**Input**//
        for (int inum = 0; inum < IN; inum++) {
            m = mArray.get(inum);
            xN =xNxM[0][inum];
            xM = xNxM[1][inum];
        for(int i=1;i<xN-1; i++){
            for(int j =1;j<xM-1;j++){ 
                if((m[i][j]<m[i-1][j])&&(m[i][j]<m[i][j-1])){    // Условие начала уклона. элемент i,j - предположительно начало озера. Северо-Западный угол
                    if(m[i-1][j]>m[i][j-1]){                     //Сравниваем борты впадины и выбираем                         
                        minBorder = m[i][j-1];
                    }else{
                        minBorder = m[i-1][j];
                    }     
                    mB = new boolean [xN][xM];
                    mB[i][j-1]=true;
                    mB[i-1][j]=true;
                    mB[i][j]=true;
                    tList = new TraceList();                   
                    tList.add(m[i][j], minBorder, i, j);
                    trace(i,j,m[i][j]);
                    
                    //**По результатам трассировки увеличиваем все элементы трассы до значения minBorder **//
                    for(int k=0;k < tList.size();k++){  
                        if(tList.getElement(k)< minBorder){
                             result = result+(minBorder-m[tList.getI(k)][tList.getJ(k)]);
                             m[tList.getI(k)][tList.getJ(k)] = minBorder;
                        }
                        
                    }                        
                        
                    //**В случае если minBorder < чем некоторые элементы трассы.Проверяем трассу на наличие внутренних озёр.Т.е. ищем участки где элементы трассы сами являются бортами для себя**//  
                    if(minBorder < tList.maxElem()){                         
                        int minLimit;
                        if(minBorder < tList.minElem()){
                            minLimit = tList.minElem();
                        }else{
                            minLimit = minBorder;
                        }
                        mB = new boolean [xN][xM];                        
                        for(int x = tList.maxElem(); x > minLimit; x-- ){
                            tList = new TraceList();
                            minBorder = x;
                            mB[i][j-1]=true;
                            mB[i-1][j]=true;
                            mB[i][j]=true;
                            tList = new TraceList();
                            tList.add(m[i][j], minBorder, i, j);
                            trace(i,j,m[i][j]);
                            for(int y=0;y < tList.size();y++){
                                tList.testPrint(y);
                                if(tList.getElement(y)< minBorder){
                                    result = result+(minBorder-m[tList.getI(y)][tList.getJ(y)]);
                                    m[tList.getI(y)][tList.getJ(y)] = minBorder;
                                }
                            }
                        } 
                    }
                } 
            }                        
        }
        System.out.println(result);       
        result = 0;
            
      }
            
    }
        
    
    public static void trace(int i,int j,int elem){ 
        /**Проверка.Есть ли соседние элементы меньшие minBorder. Является ли соседние элементы краями острова**/
        if((m[i-1][j]<minBorder)&&((i-1)==0)&&(mB[i-1][j]==false)){    //Верх .
            minBorder = m[i-1][j];
            //tList.newBorder(i, j, minBorder);
        }                                                       
        if((m[i+1][j]<minBorder)&&((i+1)==xN-1)&&(mB[i+1][j]==false)){ //Низ .
            minBorder = m[i+1][j];
         //   tList.newBorder(i, j, minBorder);
        }   
        if((m[i][j+1]<minBorder)&&((j+1)==xM-1)&&(mB[i][j+1]==false)){ //Право .
            minBorder = m[i][j+1];
        //    tList.newBorder(i, j, minBorder);
        }   
        if((m[i][j-1]<minBorder)&&((j-1)==0)&&(mB[i][j-1]==false)){    //Лево .
            minBorder = m[i][j-1];
       //     tList.newBorder(i, j, minBorder);
        }   
        /**Просматриваем все соседние элементы**/
        if(mB[i-1][j]==false){                                        //Верх . Если не промиотрен
            if(m[i-1][j]>= minBorder){                                //Верхний элемент больше чем minBorder.
                mB[i-1][j]= true;                                     //Элемент просмотрен
            }else{                                                    //Если элемент меньше чем minBorder.
                tList.add(m[i-1][j], minBorder, i-1, j);                //Добавляем элемент в список трассировки
                mB[i-1][j]= true;
                trace(i-1,j,m[i-1][j]);                
            }
        }
        if(mB[i+1][j]==false){                                        //Низ . Если не просмотрен
            if(m[i+1][j]>= minBorder){                                //Верхний элемент больше чем minBorder.
                mB[i+1][j]= true;                                     //Элемент просмотрен
            }else{                                                    //Если элемент меньше чем minBorder.
                tList.add(m[i+1][j], minBorder, i+1, j);                //Добавляем элемент в список трассировки
                mB[i+1][j]= true;
                trace(i+1,j,m[i+1][j]);                                 
            }
        }
        if(mB[i][j+1]==false){                                        //Право . Если не просмотрен
            if(m[i][j+1]>= minBorder){                                //Верхний элемент больше чем minBorder.
                mB[i][j+1]= true;                                     //Элемент просмотрен
            }else{                                                    //Если элемент меньше чем minBorder.
                tList.add(m[i][j+1], minBorder, i, j+1);                //Добавляем элемент в список трассировки
                mB[i][j+1]= true;  
                trace(i,j+1,m[i][j+1]);                                 
            }
        }
        if(mB[i][j-1]==false){                                        //Лево . Если не промиотрен
            if(m[i][j-1]>= minBorder){                                //Верхний элемент больше чем minBorder.
                mB[i][j-1]= true;                                     //Элемент просмотрен
            }else{                                                    //Если элемент меньше чем minBorder.
                tList.add(m[i][j-1], minBorder, i, j-1);                //Добавляем элемент в список трассировки
                mB[i][j-1]= true; 
                trace(i,j-1,m[i][j-1]);                                 
            }
        }
    }
}
