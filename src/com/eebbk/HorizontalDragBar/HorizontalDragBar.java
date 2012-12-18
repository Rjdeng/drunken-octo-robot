/***********************************************************************************
 * Copyright (C)2012, 步步高教育电子有限公司 
 * PROJECT NAME: ScrollButton
 * FILE NAME: HorizontalDragBar.java 
 * AUTHOR(S): Rjdeng 
 * CREATED DATE: 2012-11-28下午2:33:59
 ***********************************************************************************/

package com.eebbk.HorizontalDragBar;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.eebbk.Hscrollview.R;
import com.eebbk.Hscrollview.TerminatorScrollView;
import com.eebbk.Interface.EEBBKOnChangedListener;

public class HorizontalDragBar extends LinearLayout implements OnCheckedChangeListener
{
	private boolean newFlag = false;
	private LayoutParams layoutParams;
	private TerminatorScrollView mTerminatorScrollView;
	private EEBBKOnChangedListener mOnChangedListener;
	private RadioGroup mRadioGroup;
	private Handler mHandler;
	private int checkIndex = 0;
	
	public HorizontalDragBar( Context context, AttributeSet attrs )
	{
		super( context, attrs );
		// TODO Auto-generated constructor stub
		this.newFlag = false;
		
		mHandler = new Handler( )
		{
			public void handleMessage( android.os.Message msg )
			{
				System.out.printf( "更新数据!\n" );
				postInvalidate( );
			};
		};
	}
	
	public HorizontalDragBar( Context context, List<String> dataList )
	{
		super( context );
		this.newFlag = true;
		InitView( context, dataList );
	}
	
	public void SetListData( Context context, List<String> dataList )
	{
		InitView( context, dataList );
		mHandler.sendMessage( new Message( ) );
	}
	
	private void InitView( Context context, List<String> dataList )
	{
		List<String> localList = dataList;
		//设置方向（HORIZONTAL\VERTICAL）
		this.setOrientation( HORIZONTAL );
		this.layoutParams = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT );
		this.mTerminatorScrollView = new TerminatorScrollView( context );
		this.mRadioGroup = new RadioGroup( context );
		
		mRadioGroup.setLayoutParams( layoutParams );
		//设置方向（HORIZONTAL\VERTICAL）
		mRadioGroup.setOrientation( HORIZONTAL );
		///mRadioGroup.setBackgroundColor( ObjectAttrs.backgroundColor );
		mRadioGroup.setBackgroundColor( Color.YELLOW );
		mRadioGroup.setOnCheckedChangeListener( this );
		
		System.out.printf( "数据总数=%d\n", localList.size( ) );
		
		for ( int i = 0; i < localList.size( ); i++ )
		{
			RadioButton radioButton = (RadioButton) LayoutInflater.from( context ).inflate( R.layout.hv_radiobutton, null );

			radioButton.setText( localList.get( i ) );
			radioButton.setId( i );
			mRadioGroup.addView( radioButton, i );
			
			//设置选择项
			if( getCheckIndex( ) == i )
			{
				radioButton.setChecked( true );
			}
			System.out.printf( "生成数据=%d\n", i );
		}
		
		mTerminatorScrollView.setLayoutParams( layoutParams );
		mTerminatorScrollView.addView( mRadioGroup, layoutParams );
		mTerminatorScrollView.setBackgroundColor( Color.YELLOW);
		
		this.addView( mTerminatorScrollView, layoutParams );
	}
	
	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub
		System.out.println( "###onDraw" );
		super.onDraw( canvas );
	}
	
	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh )
	{
		super.onSizeChanged( w, h, oldw, oldh );
	}
	
	@Override
	protected void onFinishInflate( )
	{
		// TODO Auto-generated method stub
		super.onFinishInflate( );
	}
	
	@Override
	protected void onLayout( boolean changed, int l, int t, int r, int b )
	{
		// TODO Auto-generated method stub
		super.onLayout( changed, l, t, r, b );
	}
	
	//	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec )
	{
		// TODO Auto-generated method stub
		super.onMeasure( widthMeasureSpec, heightMeasureSpec );
	}
	
	@Override
	protected void onAttachedToWindow( )
	{
		// TODO Auto-generated method stub
		if( newFlag == true )
		{
			setLayoutParams( new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT ) );
		}
		
		super.onAttachedToWindow( );
	}
	
	public void setEEBBKOnChangedListener( EEBBKOnChangedListener l )
	{
		mOnChangedListener = l;
	}
	
	@Override
	public void onCheckedChanged( RadioGroup group, int checkedId )
	{
		// TODO Auto-generated method stub
		if( mOnChangedListener != null )
		{
			mOnChangedListener.onChanged( checkedId );
		}
	}

	public int getCheckIndex( )
	{
		return checkIndex;
	}

	public void setCheckIndex( int checkIndex )
	{
		this.checkIndex = checkIndex;
	}
}
