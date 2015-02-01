package rover.nicta.joystick;

/**
 * Created by johnlam on 2/01/15.
 *
 * The view for a simple joystick
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class JoystickView extends View {

    private float firstTouchX;
    private float firstTouchY;
    private float curTouchX;
    private float curTouchY;
    private boolean touching;
    private Paint paint;


    public JoystickView(Context context, AttributeSet attrs) {
        super(context,attrs);
        touching = false;
        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    public float getVelocity(){
        if(touching){
            double dx = (firstTouchX - curTouchX)/this.getWidth();
            double dy = (firstTouchY - curTouchY)/this.getHeight();
            return (float)Math.sqrt(dx * dx + dy * dy);
        }
        return 0f;
    }

    public float getAngle(){
        if(touching){
            double dx = -(firstTouchX - curTouchX);
            double dy = -(firstTouchY - curTouchY);
            return (float)Math.atan2(dy , dx);
        }
        return 0f;
    }

    @Override

    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstTouchX = e.getX();
                firstTouchY = e.getY();
                curTouchX = e.getX();
                curTouchY = e.getY();
                touching = true;
                break;
            case MotionEvent.ACTION_MOVE:
                curTouchX = e.getX();
                curTouchY = e.getY();
                break;
            case MotionEvent.ACTION_UP:
                touching = false;
                break;
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);
        if(touching) {
            canvas.drawCircle(curTouchX, curTouchY, 50, paint);
            canvas.drawLine(firstTouchX,firstTouchY,curTouchX,curTouchY,paint);
        }
        this.invalidate();

    }

}
