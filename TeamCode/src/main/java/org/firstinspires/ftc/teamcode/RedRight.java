/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Autonomous OpMode for the case where we are on the red team on the right side of the field
 */
@Autonomous
public class RedRight extends BaseAutonomous {

    @Override
    public void runOpMode() {
        initOpMode();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        clamp(true);
        sleep(500);
        // move a clamp up a little bit more than halfway
        lift(0.6);
        // set color servo down
        servo5.setPosition(0.5);
        servo5.setPosition(0.065);
        sleep(2000); // ???
        int currentColor = Color.rgb(color0.red(), color0.green(), color0.blue());
        // test for blue
        if (getSaturation(currentColor) >= 0.5
                && getHue(currentColor) > 190 && getHue(currentColor) < 250) {
            //Pick up servo a bit and then move backwards to knock of jewel
            servo5.setPosition(0.085);
            moveInch(-1.8);
        } else {
            servo5.setPosition(0.085);
            moveInch(2);
            servo5.setPosition(1);
            moveInch(-2.2);
        }
        //completely pick up servo
        servo5.setPosition(1);
        sleep(2000);
        // deposit glyph in safe zone
        moveInch(-21);
        sleep(200);
        turn(90);
        sleep(200);
        moveInch(11);
        sleep(200);
        turn(90);
        sleep(200);
        lift(0);
        sleep(200);
        clamp(false);
        sleep(200);
        moveInch(10);
    }
}