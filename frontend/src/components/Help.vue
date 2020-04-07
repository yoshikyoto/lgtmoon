<template>
  <div>
    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('help.about.title') }}</h2>
      {{ $t('help.about.description') }}
      {{ $t('help.about.gif') }}
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('help.howtouse.title') }}</h2>

      {{ $t('help.howtouse.description') }}

      <h3 class="help-section-title">{{ $t('help.howtouse.keyword.title') }}</h3>
      <ol>
        <li>{{ $t('help.howtouse.keyword.step1') }}</li>
        <li>{{ $t('help.howtouse.keyword.step2') }}</li>
      </ol>

      <h3>{{ $t('help.howtouse.url.title') }}</h3>
      <ol>
        <li>{{ $t('help.howtouse.url.step1') }}</li>
      </ol>

      <h3>{{ $t('help.howtouse.upload.title') }}</h3>
      <ol>
        <li>{{ $t('help.howtouse.upload.step1') }}</li>
        <li>{{ $t('help.howtouse.upload.step2') }}</li>
        <li>{{ $t('help.howtouse.upload.step3') }}</li>
      </ol>
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('help.notice.title') }}</h2>
      <ul>
        <li>{{ $t('help.notice.notice1') }}</li>
        <li>{{ $t('help.notice.notice2') }}</li>
        <li>{{ $t('help.notice.notice3') }}</li>
        <li>{{ $t('help.notice.notice4') }}</li>
        <li>{{ $t('help.notice.notice5') }}</li>
      </ul>

      <h3>{{ $t('help.privacypolicy.title') }}</h3>
      <ul>
        <li>{{ $t('help.privacypolicy.description') }}</li>
        <li>
          <a href="https://www.google.com/settings/ads" target="_blank" rel="noopener" >
            {{ $t('help.privacypolicy.googlelink') }}
          </a>
        </li>
        <li>
          <a href="https://www.aboutads.info/" target="_blank" rel="noopener" >
            {{ $t('help.privacypolicy.thirdpartylink') }}
          </a>
        </li>
      </ul>
    </section>
    <FavoriteHelp />

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">LGTMとは</h2>
      GitHubなどでプルリクエストをレビューした時、「Looks Good To Me」という言葉を略して「LGTM」と書くことがあります。
      （「よさそうです」くらいの意味で使われます。）
      このLGTMをちょっと楽しい画像にすることで、レビューすることやプルリクを出すことがちょっと楽しくなります。<br>
      <img src="https://qiita-image-store.s3.amazonaws.com/0/29024/373aad85-d1aa-96d7-026c-66b3587ae536.png" class="center">
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">{{ $t('help.contact.title') }}</h2>
      {{ $t('help.contact.description') }}
      <ul>
        <li>
          {{ $t('help.contact.twitter') }}
          <a href="https://twitter.com/yoshiki_utakata" target="_blank">
            yoshiki_utakata
          </a>
        </li>
        <li>
          GitHub
          <a href="https://github.com/yoshikyoto/lgtmoon" target="_blank">
            yoshikyoto/lgtmoon
          </a>
        </li>
        <li>
          {{ $t('help.contact.blog') }}
          <a href="https://www.utakata.work/" target="_blank">https://www.utakata.work/</a>
        </li>
        <li>{{ $t('help.contact.article') }}
          <ul>
            <li>
              <a href="http://qiita.com/yoshikyoto/items/4910b1a4bcefe7f5ab8c" target="_blank">
                LGTM画像を驚くほど簡単に作れるWebサービス with Scala
              </a>
            </li>
            <li>
              <a href="https://www.utakata.work/archive/category/LGTMoon" target="_blank">
                ブログのLGTMoon関連記事
              </a>
            </li>
          </ul>
        </li>
      </ul>
    </section>

    <section class="lgtmoon-section center">
      <h2 class="help-section-title">Donation</h2>
      <div>
        <ul>
          <li>
            <a href="http://amzn.asia/0UX34TT" target="_blank">
              Amazon欲しい物リスト
            </a>
          </li>
        </ul>
      </div>
    </section>
  </div>
</template>


<script>
  import FavoriteHelp from '@/components/FavoriteHelp'

  export default {
    name: 'Help',
    components: {
      FavoriteHelp
    }
  }
</script>
