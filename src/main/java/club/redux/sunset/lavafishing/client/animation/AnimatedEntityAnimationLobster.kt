package club.redux.sunset.lavafishing.client.animation

import net.minecraft.client.animation.AnimationChannel
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.animation.Keyframe
import net.minecraft.client.animation.KeyframeAnimations


object AnimatedEntityAnimationLobster {
    val WALK: AnimationDefinition = AnimationDefinition.Builder.withLength(0.75f).looping()
        .addAnimation(
            "FR2",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-4.65f, -8.86f, 0.36f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(3.48f, 6.65f, 0.2f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone19",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(6.6f, 7.13f, 0.52f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(-6.79f, -7.36f, 0.44f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone22",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-4.32f, -6.14f, 0.23f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(4.32f, 6.14f, 0.23f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "FR",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-13.09f, -11.72f, 1.35f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(1.84f, 1.69f, 0.03f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "MID",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, -0.58f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone12",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(1.34f, 1.75f, 0.13f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(-6.13f, -7.92f, 0.42f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "END",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "a2",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "right_tong",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone4",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "tail",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "head",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        ).build()
    val SWIM: AnimationDefinition = AnimationDefinition.Builder.withLength(1.25f).looping()
        .addAnimation(
            "whole",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 10f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.posVec(0f, 10f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 10f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.posVec(0f, 10f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "whole",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "MID",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.125f, KeyframeAnimations.degreeVec(-27.02f, -3.22f, -3.3f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-44.52f, -3.22f, -3.3f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(-27.02f, -3.22f, -3.3f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone13",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone14",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "FR",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.125f, KeyframeAnimations.degreeVec(-25.69f, 20.02f, -9.47f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-38.19f, 20.02f, -9.47f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(-25.69f, 20.02f, -9.47f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "a2",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.16766666f, KeyframeAnimations.degreeVec(-12.55f, -4.88f, 1.08f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.degreeVec(-17.55f, -4.88f, 1.08f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.7916766f, KeyframeAnimations.degreeVec(-15.05f, -4.88f, 1.08f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "right_tong",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "a",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.16766666f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.7916766f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(-30f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone2",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone4",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-12.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(-17.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone5",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "tail",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.0834333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone27",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.625f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone29",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "END",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.125f, KeyframeAnimations.degreeVec(-31.65f, -4.89f, -5.33f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(-31.63f, -6.33f, -4.59f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(-46.63f, -6.33f, -4.59f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "left_legs",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "FR2",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.16766666f, KeyframeAnimations.degreeVec(32.14f, -2f, 3.89f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone19",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.posVec(0f, 0f, 1.5f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone19",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(21.99f, -24.28f, -8.4f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.4583433f, KeyframeAnimations.degreeVec(36.96f, -25.1f, -10.17f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.75f, KeyframeAnimations.degreeVec(21.99f, -24.28f, -8.4f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone22",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.16766666f, KeyframeAnimations.degreeVec(60f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.3433333f, KeyframeAnimations.degreeVec(69.56f, -2.74f, -1.36f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(60f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone25",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.3433333f, KeyframeAnimations.degreeVec(-25.38f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.degreeVec(-25f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        ).build();
}