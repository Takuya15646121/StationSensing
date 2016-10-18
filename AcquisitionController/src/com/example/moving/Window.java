package com.example.moving;

import java.util.LinkedList;

import tuat.daily.phonepos.feature.name.Axis;

public class Window {

	
	private LinkedList<Double> window;
	private int window_size=0;//ウィンドウのサイズ
	private double slide_rate=1;//ウィンドウのスライド率
	private Axis axis_name;
	
	private int internal_counter = 0;//内部カウンタ
	
	
	/**
	 * コンストラクタ<br>
	 * リストの初期化，ウィンドウサイズ，スライド率の初期化
	 * @param w_size ウィンドウのサイズ
	 * @param s_rate スライド率
	 */
	public Window(int w_size,double s_rate,Axis axisname){
		this.window_size = w_size;
		if(s_rate == 0 ){
			this.slide_rate = 1;
		} else {
			this.slide_rate = s_rate;

		}
		this.window = new LinkedList<Double>();
		this.axis_name = axisname;
		
	}
	
	/**
	 * ウィンドウへの要素の挿入
	 * @param elem 挿入する要素
	 * @return そのウィンドウが利用可能か
	 */
	public boolean add(double elem){
		
		//ウィンドウが溜まりきっていない状態の処理
		if(window.size() < window_size){
			window.offer(elem);//要素の追加
			if(++internal_counter%window_size == 0){
				return true;
			}//内部カウンタの追加
			return false;
		}
		
		//ウィンドウがたまった場合の処理
		else if(window.size() == window_size){
			window.poll();//先頭の要素の削除
			window.offer(elem);//要素の追加
			//スライド分だけ溜まった場合
			if(++internal_counter%(window_size*(slide_rate))==0){
				
				return true;
			}
			//そうでない場合
			else{ 
				return false;
			}
		}
		else{//windowの大きさを超えている場合
			window.poll();//要素を減らす
			return false;
		}
		
	}

	/**
	 * 配列のゲッター
	 * @return ウィンドウ(Double型配列)
	 */
	public Double[] getArray(){
		return this.window.toArray(new Double[0]);
	}

	public Axis getAxisName(){
		return this.axis_name;
	}

	public void clearWindow(){
		this.window.clear();
	}
	

}