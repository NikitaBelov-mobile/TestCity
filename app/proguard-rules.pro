# Add project specific ProGuard rules here.

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.example.testcity.**$$serializer { *; }
-keepclassmembers class com.example.testcity.** {
    *** Companion;
}
-keepclasseswithmembers class com.example.testcity.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep class com.example.testcity.domain.model.** { *; }
-keep class com.example.testcity.data.remote.dto.** { *; }
