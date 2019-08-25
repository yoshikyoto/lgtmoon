import Vue from 'vue'
import App from '@/App.vue'
import VueI18n from 'vue-i18n'

import messages from './i18nMessages'

Vue.use(VueI18n)

new Vue({
  i18n: new VueI18n({
    locale: 'en',
    fallbackLocale: 'en',
    messages: messages,
  }),
  render: h => h(App)
}).$mount('#app')
