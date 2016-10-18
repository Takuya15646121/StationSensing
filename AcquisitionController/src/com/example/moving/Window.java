package com.example.moving;

import java.util.LinkedList;

import tuat.daily.phonepos.feature.name.Axis;

public class Window {

	
	private LinkedList<Double> window;
	private int window_size=0;//�E�B���h�E�̃T�C�Y
	private double slide_rate=1;//�E�B���h�E�̃X���C�h��
	private Axis axis_name;
	
	private int internal_counter = 0;//�����J�E���^
	
	
	/**
	 * �R���X�g���N�^<br>
	 * ���X�g�̏������C�E�B���h�E�T�C�Y�C�X���C�h���̏�����
	 * @param w_size �E�B���h�E�̃T�C�Y
	 * @param s_rate �X���C�h��
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
	 * �E�B���h�E�ւ̗v�f�̑}��
	 * @param elem �}������v�f
	 * @return ���̃E�B���h�E�����p�\��
	 */
	public boolean add(double elem){
		
		//�E�B���h�E�����܂肫���Ă��Ȃ���Ԃ̏���
		if(window.size() < window_size){
			window.offer(elem);//�v�f�̒ǉ�
			if(++internal_counter%window_size == 0){
				return true;
			}//�����J�E���^�̒ǉ�
			return false;
		}
		
		//�E�B���h�E�����܂����ꍇ�̏���
		else if(window.size() == window_size){
			window.poll();//�擪�̗v�f�̍폜
			window.offer(elem);//�v�f�̒ǉ�
			//�X���C�h���������܂����ꍇ
			if(++internal_counter%(window_size*(slide_rate))==0){
				
				return true;
			}
			//�����łȂ��ꍇ
			else{ 
				return false;
			}
		}
		else{//window�̑傫���𒴂��Ă���ꍇ
			window.poll();//�v�f�����炷
			return false;
		}
		
	}

	/**
	 * �z��̃Q�b�^�[
	 * @return �E�B���h�E(Double�^�z��)
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