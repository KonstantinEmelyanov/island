/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package island;

/**
 *
 * @author Emelyanov Konstantin
 */
public class TraceList {
    int[][] data = new int[4][0]; 
    void add(int elem, int minBorder, int i,int j){
        int[][] tmp = new int[4][data[0].length+1];
        for(int k = 0;k<data[0].length;k++){
        tmp[0][k] = data[0][k];
        tmp[1][k] = data[1][k];
        tmp[2][k] = data[2][k];
        tmp[3][k] = data[3][k];
    }
    
    tmp[0][data[0].length] = elem;
    tmp[1][data[0].length] = minBorder;
    tmp[2][data[0].length] = i;
    tmp[3][data[0].length] = j;
    this.data=tmp;
    }
    void newBorder(int i, int j, int newBorder){
        for(int n = 0; n<data[0].length; n++ )
            if((data[2][n] == i)&&(data[3][n]== j)){
                data[1][n]= newBorder;
            }
    }    
    int size(){
        return data[0].length;    
    }
    int getI(int k){
        return data[2][k];
    }
    int getJ(int k){
        return data[3][k];
    }
    int getElement(int k){
        return data[0][k];
    }
    int maxElem(){
        int max = data[0][0];
        for(int i=0;i<data[0].length;i++){
            if(max< data[0][i]){
                max=data[0][i];
            }            
        }
        return max;
    }    
    int minElem(){
        int min = data[0][0];
        for(int i=0;i<data[0].length;i++){
            if(min> data[0][i]){
                min=data[0][i];
            }            
        }
        return min;
    }    
    void testPrint(int i){
        System.out.print(" "+data[0][i]);
        System.out.print(" "+data[1][i]);
        System.out.print(" "+data[2][i]);
        System.out.println(" "+data[3][i]);
    }
}
