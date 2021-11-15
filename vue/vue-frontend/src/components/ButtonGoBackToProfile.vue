<template>
    <button @click="goBack()" class="btn btn1">Revenir au profile</button>
    <br />
</template>

<script>
import router from "./../router/index";

export default {
    name: 'ButtonBackToProfile',
    methods: {
        goBack() {
            router.push("/profile")
        },
    }
}
</script>
