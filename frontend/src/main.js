import Vue from 'vue'
import App from '@/App.vue'
import VueI18n from 'vue-i18n'
import messages from './message'

Vue.use(VueI18n);

const i18n = new VueI18n({
  // en-US, ja-JP などの en, jp の部分を取得
  locale: navigator.language.split('-')[0],
  // 対応する言語が無い場合は英語にする
  fallbackLocale: 'en',
  messages,
})

new Vue({
  render: h => h(App),
  i18n
}).$mount('#app');
