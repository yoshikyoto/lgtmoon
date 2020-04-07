export default {
  ja: {
    form: {
      placeholder: "キーワードで画僧検索 or URLを直接入力",
      upload: "画像アップロード",
      submit: {
        search: "検索/生成",
        generate: "LGTM画像生成"
      },
      result: {
        title: "画像検索結果からLGTMを作成",
        description: "画像を選択するとその画像を元にLGTM画像を作成します"
      },
      message: {
        generating: "生成完了までお待ちください",
        error: {
          search: "画像検索でエラーが発生しました",
          generating: "画像生成中にエラーが発生しました"
        }
      }
    },
    menu: {
      recent: "最近の画像",
      random: "ランダム",
      favorite: "お気に入り",
      help: "使い方"
    },
    star: {
      title: "お気に入り機能の使い方",
      hover: "画像の上にマウスを移動させると右上に☆が出て来ます。",
      star: "☆をクリックするとお気に入り登録されます。",
      unstar: "もう一度クリックするとお気に入りから外れます。",
      limit: "お気に入りの上限はありません。",
      save: "お気に入りはお使いのPCブラウザに保存されますので、PCを変えたりブラウザを変えたりしますとお気に入りは消えてしまいます。ご了承ください。"
    },
    help: {
      about: {
        title: "LGTMoonとは",
        description: "LGTMoonはLGTM画像をカンタンに生成・作成できるWebサイト、「LGTM画像ジェネレーター」です。",
        gif: "GIFアニメーションにも対応しています。"
      },
      howtouse: {
        title: "使い方",
        description: "LGTM画像の作成方法はいくつかあります。",
        keyword: {
          title: "キーワード検索からLGTM画像生成をする方法",
          step1: "検索欄にキーワードを入力すると画像検索結果一覧が表示されます。",
          step2: "検索結果一覧から画像をクリックすると、その画像を元にLGTM画像が生成されます。"
        },
        url: {
          title: "画像URLからLGTM画像を生成する方法",
          step1: "画像URLを検索欄に入力すると、その画像をもとにLGTM画像が生成されます。"
        },
        upload: {
          title: "画像をアップロードする方法",
          step1: "アップロードボタンをクリックします",
          step2: "ファイルを選択して画像をアップロードします",
          step3: "アップロードした画像からLGTM画像が生成されます。"
        }
      },
      notice: {
        title: "注意",
        notice1: "画像生成には数秒〜数分かかります。連打しないでください。",
        notice2: "生成に成功すると「最近の画像」に表示されます。",
        notice3: "画像生成に失敗する可能性もあります。しばらくたっても生成されない場合は、もう一度お試しいただくか、別の画像でお試しください。",
        notice4: "LGTM画像生成の際は、元画像の著作権などに注意してください。",
        notice5: "公序良俗に反する画像や違法な画像を作成しないでください。これらの画像が見つかった場合は予告なく画像を削除することがあります。"
      },
      privacypolicy: {
        title: "プライバシーポリシー",
        description: "このサイトでは、Googleや第三者配信事業者がCookieを使用して、当サイトや他のサイトへの過去のアクセス情報に基づいて広告を配信します。",
        googlelink: "Googleのパーソナライズド広告を無効にしたい",
        thirdpartylink: "サードパーティのパーソナライズド広告のCookieの利用許可設定を変更したい"
      },
      contact: {
        title: "Contact",
        description: "何か要望・ご連絡がございましたら下記にご連絡くださるか、GitHubにプルリクエストを出してください。",
        twitter: "twitter",
        blog: "ブログ",
        article: "関連記事"
      },
      donation: {
        title: "応援する"
      }
    }
  },
  en: {
    form: {
      placeholder: "Keyword to search / URL to generate",
      upload: "Upload",
      submit: {
        search: "Search and Generate",
        generate: "Generate"
      },
      result: {
        title: "Generate by search result",
        description: "Click to select source image."
      },
      message: {
        generating: "Generating, please wait.",
        error: {
          search: "Error on searching.",
          generating: "Error on generating."
        }
      }
    },
    menu: {
      recent: "Recent",
      random: "Random",
      favorite: "Stars",
      help: "Help"
    },
    star: {
      title: "How to use \"Star\"",
      hover: "Hover mouse on image and click star.",
      star: "Click star to add to your Star (bookmark).",
      unstar: "Click star again to remove from your star.",
      limit: "You can use it without cout limit.",
      save: "Star save on your browser. When you change PC or browser, your star is away."
    },
    help: {
      about: {
        title: "What is LGTMoon",
        description: "LGTMoon is LGTM image generator to generat LGTM image easily.",
        gif: "LGTMoon also support GIF image."
      },
      howtouse: {
        title: "Hot to user",
        description: "You can generate LGTM image in several ways.",
        keyword: {
          title: "Generate with image search.",
          step1: "Input keyword in textarea and search images.",
          step2: "Click an image in search result, create LGTM image from the selected image.。"
        },
        url: {
          title: "Generate with URL of source image",
          step1: "Input image URL and click Generate button to Generate from the image."
        },
        upload: {
          title: "Upload source image",
          step1: "Click \"Upload\" button.",
          step2: "Select image file and upload.",
          step3: "Generate LGTM image from uploaded file."
        }
      },
      notice: {
        title: "Notice",
        notice1: "It takes few seconds or minutes to generate. Please don't click button repeatedly.",
        notice2: "LGTM image appears in \"Recent\" section when generation is succeeded.",
        notice3: "It might fail to generate image. Please try again or use another image when it seems to be failed.",
        notice4: "Please avoid violation of copyright.",
        notice5: "We might delete images that violate the law, public order or morality."
      },
      privacypolicy: {
        title: "Privacy policy",
        description: "Google and thirdparty use Cookie that contains access history of sites to provide ADs.",
        googlelink: "Disable Google Personalized ADs.",
        thirdpartylink: "Manage Cookie settings for thirdparty ADs."
      },
      contact: {
        title: "Contact",
        description: "If you find bugs, wrong english, question or feature request, contact me or create pull request on GitHub.",
        twitter: "twitter (mainly Japanese)",
        blog: "Blog (Japanese only)",
        article: "Related Articles (Japanese only)"
      },
      donation: {
        title: "Donate"
      }
    }
  }
}
