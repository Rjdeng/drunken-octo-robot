/***********************************************************************************
 * Copyright (C)2012, �����߽����������޹�˾ 
 * PROJECT NAME: ScrollButton
 * FILE NAME: ExtendScrollView.java 
 * AUTHOR(S): Rjdeng 
 * CREATED DATE: 2012-10-31����8:07:04
 ***********************************************************************************/

package com.eebbk.Hscrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

public class TerminatorScrollView extends HorizontalScrollView
{
	private boolean firstFlag = true;
	private float saveValue = 0f;
	private Rect normal = new Rect( );
	private View inner;
	private Context context;
	
	//���캯��
	public TerminatorScrollView( Context context )
	{
		super( context );
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	//���캯��
	public TerminatorScrollView( Context context, AttributeSet attrs )
	{
		super( context, attrs );
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent ev )
	{
		// TODO Auto-generated method stub
		if( inner != null )
		{
			commOnTouchEvent( ev );
		}
		else
		{
			System.out.printf( "inner==null\n" );
		}
		
		return super.onTouchEvent( ev );
	}
	
	//ӡ��1
	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh )
	{
		// TODO Auto-generated method stub
		
		if( getChildCount( ) > 0 )
		{
			inner = getChildAt( 0 );
		}
		super.onSizeChanged( w, h, oldw, oldh );
	}
	
	//ӡ��2
	@Override
	protected void onFinishInflate( )
	{
		// TODO Auto-generated method stub
		
		if( getChildCount( ) > 0 )
		{
			inner = getChildAt( 0 );
		}
		super.onFinishInflate( );
	}
	
	public void commOnTouchEvent( MotionEvent ev )
	{
		int action = ev.getAction( );
		
		///System.out.printf( "###action=%d\n", action );
		
		switch( action )
		{
			case MotionEvent.ACTION_DOWN:
			{
				//do nothing
			}
			break;
			
			case MotionEvent.ACTION_UP:
			{
				firstFlag = true;
				setValue( 0f );
				if( isNeedAnimation( ) )
				{
					animation( );
				}
			}
			break;
			
			case MotionEvent.ACTION_MOVE:
			{
				int deltaX = 0;
				float nowX = ev.getX( );
				
				// �������������������ʱ�Ͳ����ٹ�������ʱ�ƶ�����   
				if( isNeedMove( ) )
				{
					if( firstFlag == true )
					{
						setValue( nowX );
						firstFlag = false;
					}
					
					deltaX = (int) ( getValue( ) - nowX );
					// ����   
					scrollBy( deltaX, 0 );
					
					if( normal.isEmpty( ) )
					{
						// ���������Ĳ���λ��   
						normal.set( inner.getLeft( ), inner.getTop( ), inner.getRight( ), inner.getBottom( ) );
					}
					
					// �ƶ�����   
					///System.out.printf( "###%d,%d,%d,%f\n", deltaX, inner.getLeft( ), inner.getRight( ), nowX );
					inner.layout( -deltaX, inner.getTop( ), inner.getMeasuredWidth( ) - deltaX, inner.getBottom( ) );
				}
			}
			break;
			
			default:
			break;
		}
	}
	
	// ���������ƶ�   
	public void animation( )
	{
		// �����ƶ�����   
		System.out.printf( "###�����ƶ�����\n" );
		TranslateAnimation translateAnimation = new TranslateAnimation( inner.getLeft( ), normal.left, 0, 0 );
		
		translateAnimation.setInterpolator( this.context, android.R.anim.overshoot_interpolator );
		translateAnimation.setDuration( 1000 );
		translateAnimation.setFillAfter( true );
		inner.startAnimation( translateAnimation );
		// ���ûص������Ĳ���λ��   
		inner.layout( normal.left, normal.top, normal.right, normal.bottom );
		normal.setEmpty( );
	}
	
	// �Ƿ���Ҫ��������   
	public boolean isNeedAnimation( )
	{
		System.out.printf( "###�Ƿ���Ҫ��������  \n" );
		return !normal.isEmpty( );
	}
	
	// �Ƿ���Ҫ�ƶ�����   
	public boolean isNeedMove( )
	{
		int offset = inner.getMeasuredWidth( ) - getWidth( );
		int scrollX = getScrollX( );
		///System.out.printf( "###%d,%d\n", offset, scrollX );
		if( scrollX == 0 || scrollX == offset )
		{
			return true;
		}
		return false;
	}
	
	private void setValue( float value )
	{
		this.saveValue = value;
	}
	
	private float getValue( )
	{
		return this.saveValue;
	}
}
