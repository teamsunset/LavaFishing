package club.redux.sunset.lavafishing.client.animation

import net.minecraft.client.animation.AnimationChannel
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.animation.Keyframe
import net.minecraft.client.animation.KeyframeAnimations


object AnimatedEntityAnimationEel {
    val WALK: AnimationDefinition = AnimationDefinition.Builder.withLength(1f).looping()
        .addAnimation(
            "body",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5f, KeyframeAnimations.degreeVec(0f, 66.98f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.9167666f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "B3",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.3433333f, KeyframeAnimations.degreeVec(0f, -175.28f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.8343334f, KeyframeAnimations.degreeVec(0f, -12f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR
                )
            )
        )
        .addAnimation(
            "M",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.16766666f, KeyframeAnimations.degreeVec(0f, 95.87f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.5834334f, KeyframeAnimations.degreeVec(0f, -54.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "B6",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.2916767f, KeyframeAnimations.degreeVec(0f, 168.96f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.6766666f, KeyframeAnimations.degreeVec(0f, 60.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "T",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.25f, KeyframeAnimations.degreeVec(0f, -12.07f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.6766666f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        )
        .addAnimation(
            "bone8",
            AnimationChannel(
                AnimationChannel.Targets.ROTATION,
                Keyframe(
                    0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.08343333f, KeyframeAnimations.degreeVec(0f, -140.58f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    0.375f, KeyframeAnimations.degreeVec(0f, 42.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                ),
                Keyframe(
                    1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM
                )
            )
        ).build();
}