class Locale {

  /**
   * @returns {string}
   */
  getBrowserLocale() {
    return navigator.language.split('-')[0]
  }

  /**
   * HTMLのscriptタグなどで予め locale を定義しておいてください
   * @returns {string}
   */
  getLocale() {
    return locale;
  }
}

export default new Locale()
