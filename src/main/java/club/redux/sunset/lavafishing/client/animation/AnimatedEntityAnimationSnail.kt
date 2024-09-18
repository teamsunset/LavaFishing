package club.redux.sunset.lavafishing.client.animation

import net.minecraft.client.animation.AnimationChannel
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.animation.Keyframe
import net.minecraft.client.animation.KeyframeAnimations


object AnimatedEntityAnimationSnail {
    val WALK: AnimationDefinition = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "shell",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.08343333f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.posVec(0f, 1.25f, -0.25f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.posVec(0f, 1.25f, -0.25f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "shell",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "shell",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.08343333f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.scaleVec(1.0, 1.05, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
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
                    0.08343333f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.posVec(0f, 1.25f, -0.75f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
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
                    0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "body",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.08343333f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.scaleVec(1.0, 1.3, 0.8),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "d",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.posVec(0f, 0f, 1f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone4",
            AnimationChannel(
                AnimationChannel.Targets.POSITION,
                Keyframe(
                    0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.posVec(0f, 0f, 1.25f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.posVec(0f, 0f, 1.25f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "bone4",
            AnimationChannel(
                AnimationChannel.Targets.SCALE,
                Keyframe(
                    0f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.125f, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.scaleVec(1.0, 0.9, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5416766f, KeyframeAnimations.scaleVec(1.0, 0.9, 1.0),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        ).build();
}