class Locale {

  /**
   * @returns {string}
   */
  getLocale() {
    return navigator.language.split('-')[0]
  }
}

export default new Locale()
