import Vue from 'vue'
import App from '@/App.vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n);

const messages = {};
const i18n = new VueI18n({
  locale: 'ja',
  messages,
})

new Vue({
  render: h => h(App),
  i18n
}).$mount('#app');
