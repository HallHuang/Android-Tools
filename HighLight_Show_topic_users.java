

/**
 * 1.正则表达式，由于查询获取文本中的#..#包括的话题和@LinkToUser 后的用户
 */

    private static final String TOPIC = "#([^#]+?)#";

    // \\p{InCJK_Unified_Ideographs}表示兼容 CJK（中文、日文、韓文）統一表意字符
    private static final String AT = "@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26} ";

    private static final String ALL = "(" + AT + ")" + "|" + "(" + TOPIC + ")";

    /**
     * 查询艾特和话题事件
     */
    public static ArrayList<String> findAction(String s) {
        Pattern p = Pattern.compile(ALL);
        Matcher m = p.matcher(s);

        ArrayList<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }
	
/**
 * 1end
 */

/**
 * 2.EditText 输入文本内容的突出/高亮显示
 */

//手动输入文本时的即使对应处理
        mEtFeedInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                // 查找话题和@
                String content = s.toString();
                mActionList.clear();
                mActionList.addAll(Utils.findAction(content));
                // 首先移除之前设置的colorSpan
                Editable editable = mEtFeedInfo.getText();
                if (editable == null) {
                    return;
                }

                //保存并清零文本的最近一次的样式列表
                for (ForegroundColorSpan mColorSpan : mColorSpans) {
                    editable.removeSpan(mColorSpan);
                }
                mColorSpans.clear();

                // 对 #话题内容# 或 @联系人 设置前景色colorSpan
                int findPos = 0;
                for (String topic : mActionList) {
                    //indexOf(int chars, int fromIndex)
                    findPos = content.indexOf(topic, findPos);
                    if (findPos != -1) {
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(3, 169, 244));
                        editable.setSpan(colorSpan, findPos, findPos = findPos + topic.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mColorSpans.add(colorSpan);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

/**
 * 2end
 */