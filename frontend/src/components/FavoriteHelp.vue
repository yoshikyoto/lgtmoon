<template>
  <section class="lgtmoon-section center">
    <h2 class="help-section-title">{{ $t('star.title') }}</h2>
    <p>
      {{ $t('star.hover') }}
    </p>
    <img src="/assets/help/favorite.png" class="favorite-help-cat">
    <p>
      {{ $t('star.star') }}
      {{ $t('star.unstar') }}
    </p>
    <p>
      {{ $t('star.limit') }}<br>
      {{ $t('star.save') }}
    </p>
  </section>
</template>

<style scoped>
  .favorite-help-cat {
    width: 320px;
  }
</style>
