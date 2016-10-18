package com.example.moving;

import tuat.daily.phonepos.feature.base.IBaseFeature;
import tuat.daily.phonepos.feature.name.Axis;
import tuat.daily.phonepos.window.filter.AccelerometerCalculator;

public class WindowWrapper {
	
	private AccelerometerCalculator calculator;
	private Window[] acc_window;
	private Window[] mag_window;
	private final double frequency;
	
	
	public WindowWrapper(int size,double slide,double frequency){
		this.calculator = new AccelerometerCalculator();
		this.frequency = frequency;
		init(size,slide);
	}
	
	private void init(int w_size,double w_slide){
		
		acc_window = new Window[4];
		acc_window[0] = new Window(w_size,w_slide,Axis.ACC_LINEAR_X);
		acc_window[1] = new Window(w_size,w_slide,Axis.ACC_LINEAR_Y);
		acc_window[2] = new Window(w_size,w_slide,Axis.ACC_LINEAR_Z);
		acc_window[3] = new Window(w_size,w_slide,Axis.ACC_LINEAR_3AXIS);
		
		
		mag_window = new Window[4];
		Axis[] axes = new Axis[]{Axis.MAG_RAW_X,Axis.MAG_RAW_Y,Axis.MAG_RAW_Z,Axis.MAG_RAW_3AXIS};
		for(int i=0;i<mag_window.length;i++){
			mag_window[i] = new Window(w_size,w_slide,axes[i]);
		}
		
	}
	
	public boolean isAvailableAccWindow(double x,double y,double z){
		
		double[] gravity = calculator.updateGravity(new double[]{x,y,z});
		if(gravity != null){
			
			double[] user_acc = calculator.calcUserAcc(new double[]{x,y,z});
			double user_acc_mag = calcMagnitude(user_acc);
						
			boolean acc1 = acc_window[0].add(user_acc[0]);
			boolean acc2 = acc_window[1].add(user_acc[1]);
			boolean acc3 = acc_window[2].add(user_acc[2]);
			boolean accMag = acc_window[3].add(user_acc_mag);
			
			return acc1 && acc2 && acc3 && accMag; 
		} else {
			return false;
		}
		
	}
	
	
	public boolean isAvailableMagWindowMag(double x,double y,double z){
		mag_window[0].add(x);
		mag_window[1].add(y);
		mag_window[2].add(z);
		boolean result = mag_window[3].add(calcMagnitude(new double[]{x,y,z}));
		
		return result;
		
	}
	
	private double calcMagnitude(double[] val){
		return Math.sqrt(val[0]*val[0]+val[1]*val[1]+val[2]*val[2]);
	}

	public void setWindowAccQueue(IBaseFeature base){
		for(Window w:acc_window){
			base.setWindow(w.getAxisName(), w.getArray(),frequency);
		}
	}
	
	public void setMagWindowQueue(IBaseFeature base){
		for(Window w:mag_window){
			base.setWindow(w.getAxisName(), w.getArray(), frequency);
		}
	}

}
