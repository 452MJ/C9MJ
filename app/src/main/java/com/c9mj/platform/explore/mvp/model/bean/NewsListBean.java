package com.c9mj.platform.explore.mvp.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public class NewsListBean {

    /**
     * postid : PHOT23D92000100A
     * hasCover : false
     * hasHead : 1
     * replyCount : 94416
     * hasImg : 1
     * digest :
     * hasIcon : false
     * docid : 9IG74V5H00963VRO_C4P5NV46bjlishidaiupdateDoc
     * title : 北京华堂十里堡店难敌电商潮 彻底停业
     * order : 1
     * priority : 355
     * lmodify : 2016-11-01 13:27:41
     * boardid : photoview_bbs
     * ads : [{"title":"看客:八十年后的中国 观城乡\"新世界\"","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/b55941dbd35c407f942e274107158a7a20161101070925.jpeg","subtitle":"","url":"3R710001|2209018"},{"title":"珠海航展歼20惊艳亮相:如刺客一闪而过","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/0b5680c5e6dc4d7aadc56f06ba6f647d20161101111706.jpeg","subtitle":"","url":"00AN0001|2209144"},{"title":"江西九江鄱阳湖迎枯水期 又现死亡江豚","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/e8297c6ab64b41e9a3cd9a3360b60b1520161101071108.jpeg","subtitle":"","url":"00AP0001|2209046"},{"title":"沈阳一河段蜿蜒数千米 污浊如\u201c绿毯\u201d","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/bbdedd2980b4470fb1cd00c18250b5ed20161101075147.jpeg","subtitle":"","url":"00AP0001|2209074"},{"title":"尖端军事装备亮相珠海航展 公务机抢眼","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/a91300860a8840bc8de15c0fed27c0cb20161101071053.jpeg","subtitle":"","url":"00AP0001|2209045"}]
     * photosetID : 00AP0001|2209058
     * imgsum : 6
     * template : normal1
     * votecount : 89904
     * skipID : 00AP0001|2209058
     * alias : Top News
     * skipType : photoset
     * cid : C1348646712614
     * hasAD : 1
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/faa4ba6d2012475d9e05caa126d81ff220161101073147.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/069e50dcd1ac423fa6c0f69aef89b70120161101073147.jpeg"}]
     * source : 网易原创
     * ename : androidnews
     * tname : 头条
     * imgsrc : http://cms-bucket.nosdn.127.net/f3526a4a705742d9963d7e8031c5406b20161101073147.jpeg
     * ptime : 2016-11-01 07:31:56
     */

    private List<NewsItemBean> T1348647909107;

    public List<NewsItemBean> getT1348647909107() {
        return T1348647909107;
    }

    public void setT1348647909107(List<NewsItemBean> T1348647909107) {
        this.T1348647909107 = T1348647909107;
    }

    public static class NewsItemBean {
        private String postid;
        private boolean hasCover;
        private int hasHead;
        private int replyCount;
        private int hasImg;
        private String digest;
        private boolean hasIcon;
        private String docid;
        private String title;
        private int order;
        private int priority;
        private String lmodify;
        private String boardid;
        private String photosetID;
        private int imgsum;
        private String template;
        private int votecount;
        private String skipID;
        private String alias;
        private String skipType;
        private String cid;
        private int hasAD;
        private String source;
        private String ename;
        private String tname;
        private String imgsrc;
        private String ptime;
        /**
         * title : 看客:八十年后的中国 观城乡"新世界"
         * tag : photoset
         * imgsrc : http://cms-bucket.nosdn.127.net/b55941dbd35c407f942e274107158a7a20161101070925.jpeg
         * subtitle :
         * url : 3R710001|2209018
         */

        private List<AdsBean> ads;
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/faa4ba6d2012475d9e05caa126d81ff220161101073147.jpeg
         */

        private List<ImgextraBean> imgextra;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getHasImg() {
            return hasImg;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getPhotosetID() {
            return photosetID;
        }

        public void setPhotosetID(String photosetID) {
            this.photosetID = photosetID;
        }

        public int getImgsum() {
            return imgsum;
        }

        public void setImgsum(int imgsum) {
            this.imgsum = imgsum;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getVotecount() {
            return votecount;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getHasAD() {
            return hasAD;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public List<ImgextraBean> getImgextra() {
            return imgextra;
        }

        public void setImgextra(List<ImgextraBean> imgextra) {
            this.imgextra = imgextra;
        }

        public static class AdsBean {
            private String title;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ImgextraBean {
            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }
        }
    }
}
