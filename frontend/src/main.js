import Vue from 'vue'
import App from '@/App.vue'
import VueI18n from 'vue-i18n'
import messages from './message'

Vue.use(VueI18n);

console.log(locale);
const i18n = new VueI18n({
  // locale は index.html や en.html に埋め込まれている
  locale: locale,
  messages
});

new Vue({
  render: h => h(App),
  i18n
}).$mount('#app');
