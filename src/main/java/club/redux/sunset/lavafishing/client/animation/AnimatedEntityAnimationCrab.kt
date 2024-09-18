package club.redux.sunset.lavafishing.client.animation

import net.minecraft.client.animation.AnimationChannel
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.animation.Keyframe
import net.minecraft.client.animation.KeyframeAnimations

object AnimatedEntityAnimationCrab {
    val WALK: AnimationDefinition = AnimationDefinition.Builder.withLength(1.1676667f).looping()
        .addAnimation(
            "right6",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(-1.25f, -0.25f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(1f, -0.25f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.posVec(0f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(-1.8f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(-1.25f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right6",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -25f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, -50f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 15f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right7",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(0.25f, 1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.posVec(0.49f, 0.95f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(-0.75f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right7",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -19f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 15f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right8",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right8",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, 15f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, 30f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right2",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(-3.5f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(-5.75f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.posVec(-5.75f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(-3.5f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right2",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right3",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(-1.25f, -1.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(-3f, -2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.posVec(-3f, -2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(-1.25f, -1.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right3",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, 22.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 22.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right4",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -52.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, -52.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right10",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(0f, -0.04f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right10",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 7.6f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right10",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right11",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(2.25f, -1.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(2.25f, -1.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(-0.73f, 0.85f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right11",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -63.93f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -91.43f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right12",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(2f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(2f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(-0.34f, 0.3f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right12",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -30f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -30f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 11.41f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right12",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right13",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right13",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.7916766f, KeyframeAnimations.degreeVec(0f, 0f, -20.86f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "left10",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0.5f, 0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(-0.25f, 1.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.posVec(-2.25f, -1.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(0.5f, 0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0.5f, 0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left10",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -32.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 22.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left11",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0.75f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(0.75f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(-1.25f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(0.75f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0.75f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left11",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, 25f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left9",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(0f, 1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(0f, -0.75f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(0f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left9",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -15f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left7",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(2.25f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(0.25f, 2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.posVec(0.25f, 2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.posVec(-2f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left7",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -17.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left6",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0.25f, 1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(2f, 1.75f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.posVec(1f, 2.75f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.posVec(-0.3f, 2.39f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(-0.5f, 1.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0.25f, 1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left6",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -20f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left5",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, 57.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left3",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(1.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(1.25f, 0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(0.25f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.posVec(1.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(1.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left3",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -25f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left2",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(2f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(1.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(0.75f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.posVec(2f, 0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left2",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, 15f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, 25f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "body",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.3433333f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.posVec(0f, 0.1f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "body",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.3433333f, KeyframeAnimations.degreeVec(0f, 0f, -3f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.625f, KeyframeAnimations.degreeVec(0f, 0f, 3f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.degreeVec(0f, 0f, -3f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right_claw",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167667f, KeyframeAnimations.degreeVec(0f, 0f, 2.14f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 7.5f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, -0.25f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, -6.07f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone3",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0.75f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.20834334f, KeyframeAnimations.posVec(0.3f, 0.75f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.posVec(0.15f, 0.65f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.posVec(0.25f, 0.65f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(-0.05f, 0.65f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, 0.75f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone3",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.20834334f, KeyframeAnimations.degreeVec(-17.75f, -9.53f, 3.04f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.degreeVec(-12.5f, 0.06f, 0.21f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(-17.75f, -9.53f, 3.04f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(-12.5f, 0.06f, 0.21f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone4",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.20834334f, KeyframeAnimations.degreeVec(-12.55f, -4.88f, 1.08f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.degreeVec(-12.51f, -2.44f, 0.54f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(-12.53f, -3.91f, 0.87f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(-12.5f, -0.98f, 0.22f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone6",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.20834334f, KeyframeAnimations.degreeVec(-15.02f, -2.9f, 0.78f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.degreeVec(-12f, 0.97f, -0.26f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(-12.01f, -1.97f, 0.37f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(-10f, -0.99f, 0.16f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(-14.5f, -0.99f, 0.16f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone7",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.20834334f, KeyframeAnimations.posVec(0.2f, 1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.posVec(0f, 0.8f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.posVec(0.2f, 0.8f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0.2f, 0.6f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, 1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone7",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.20834334f, KeyframeAnimations.degreeVec(0f, -9f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.degreeVec(0f, 6f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(-2f, -2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(1.99f, 6f, -0.28f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "left_claw",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.875f, KeyframeAnimations.degreeVec(0f, 0f, -4f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        ).build();
}