/***********************************************************************************
 * Copyright (C)2012, 步步高教育电子有限公司 
 * PROJECT NAME: ScrollButton
 * FILE NAME: ExtendScrollView.java 
 * AUTHOR(S): Rjdeng 
 * CREATED DATE: 2012-10-31上午8:07:04
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
	
	//构造函数
	public TerminatorScrollView( Context context )
	{
		super( context );
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	//构造函数
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
	
	//印象1
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
	
	//印象2
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
				
				// 当滚动到最左或者最右时就不会再滚动，这时移动布局   
				if( isNeedMove( ) )
				{
					if( firstFlag == true )
					{
						setValue( nowX );
						firstFlag = false;
					}
					
					deltaX = (int) ( getValue( ) - nowX );
					// 滚动   
					scrollBy( deltaX, 0 );
					
					if( normal.isEmpty( ) )
					{
						// 保存正常的布局位置   
						normal.set( inner.getLeft( ), inner.getTop( ), inner.getRight( ), inner.getBottom( ) );
					}
					
					// 移动布局   
					///System.out.printf( "###%d,%d,%d,%f\n", deltaX, inner.getLeft( ), inner.getRight( ), nowX );
					inner.layout( -deltaX, inner.getTop( ), inner.getMeasuredWidth( ) - deltaX, inner.getBottom( ) );
				}
			}
			break;
			
			default:
			break;
		}
	}
	
	// 开启动画移动   
	public void animation( )
	{
		// 开启移动动画   
		System.out.printf( "###开启移动动画\n" );
		TranslateAnimation translateAnimation = new TranslateAnimation( inner.getLeft( ), normal.left, 0, 0 );
		
		translateAnimation.setInterpolator( this.context, android.R.anim.overshoot_interpolator );
		translateAnimation.setDuration( 1000 );
		translateAnimation.setFillAfter( true );
		inner.startAnimation( translateAnimation );
		// 设置回到正常的布局位置   
		inner.layout( normal.left, normal.top, normal.right, normal.bottom );
		normal.setEmpty( );
	}
	
	// 是否需要开启动画   
	public boolean isNeedAnimation( )
	{
		System.out.printf( "###是否需要开启动画  \n" );
		return !normal.isEmpty( );
	}
	
	// 是否需要移动布局   
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
