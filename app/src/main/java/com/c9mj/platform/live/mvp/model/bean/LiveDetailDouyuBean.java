package com.c9mj.platform.live.mvp.model.bean;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/20
 * 斗鱼直播详情
 */

public class LiveDetailDouyuBean {


    private int error;


    private DataBean data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String use_p2p;
        private String show_details;
        private String nickname;
        private String rtmp_url;
        private String anchor_city;
        private String specific_status;
        private String url;
        private String rtmp_cdn;
        private String specific_catalog;
        private int cate_id1;
        private String show_status;
        private String game_icon_url;
        private String game_name;
        private String show_time;
        private int isVertical;
        private String rtmp_live;
        private String fans;
        private String game_url;
        private String room_src;
        private String is_white_list;
        private String room_name;
        private String owner_uid;
        private String owner_avatar;
        private String vertical_src;
        private int room_dm_delay;
        private String owner_weight;
        private int is_pass_player;
        private String hls_url;
        private String room_id;
        private String cur_credit;
        private String gift_ver;
        private String low_credit;
        /**
         * middle : 271934rUcXJm0SNk_550.flv?wsAuth=095dc12f9ad494601c6f60b8a2e6f64c&token=cpn-dotamax-0-271934-e7c16d4eaed9e332cbc979018f907b0b&logo=0&expire=0
         * middle2 : 271934rUcXJm0SNk_900.flv?wsAuth=71a2e0662fe4aba50ff28491b83ca166&token=cpn-dotamax-0-271934-1e43f26bdb400a6c9396b532f48da585&logo=0&expire=0
         */

        private RtmpMultiBitrateBean rtmp_multi_bitrate;
        private int online;
        private String credit_illegal;
        private String vod_quality;
        private String cate_id;
        /**
         * ip : 119.90.49.90
         * port : 8098
         */

        private List<ServersBean> servers;
        /**
         * name : 主线路
         * cdn : ws
         */

        private List<CdnsWithNameBean> cdnsWithName;
        private List<?> black;
        /**
         * himg : http://staticlive.douyucdn.cn/upload/dygift/1606/39b578b3cb8645b54f9a1001c392a237.gif
         * pdhimg : http://staticlive.douyucdn.cn/upload/dygift/1606/31e7b2c9ad7a10dd06803e5b685dcef6.gif
         * gx : 5000
         * mobile_big_effect_icon_0 : http://staticlive.douyucdn.cn/upload/dygift/1606/f3eb5d9c1573a373aacca7466f5050ab.gif
         * cimg : http://staticlive.douyucdn.cn/upload/dygift/1606/a7ec5ed84cebf6043e20e15f55735260.gif
         * mobile_big_effect_icon_1 : http://staticlive.douyucdn.cn/upload/dygift/1606/180a0ad10fbf582e6990efd31add8426.png
         * big_effect_icon : http://staticlive.douyucdn.cn/upload/dygift/1606/33d2f977b12205e10a9a3522a1e9fd90.gif
         * pdbimg : http://staticlive.douyucdn.cn/upload/dygift/1606/7180fc8e4bf3152b778dc472fbf4857e.png
         * mobile_big_effect_icon_2 : http://staticlive.douyucdn.cn/upload/dygift/1606/fa553dcf79565f7b8a2d6e96b6c7da90.png
         * mimg : http://staticlive.douyucdn.cn/upload/dygift/1606/26f802520cf0e4d8a645259bbc1aadf3.png
         * brgb : #ff831f
         * mobile_big_effect_icon_3 : http://staticlive.douyucdn.cn/upload/dygift/1606/287527403ec8b6788a0ead58c15cb7eb.png
         * m_ef_gif_2 :
         * pimg : http://staticlive.douyucdn.cn/upload/dygift/1609/8fdc7b6395b93729eed49429d2776a73.png
         * pt : 余额不足，火箭被卡在发射塔上了
         * id : 196
         * intro : 我们的征途是星辰大海
         * pc : 50000
         * m_ef_gif_1 : http://staticlive.douyucdn.cn/upload/dygift/1606/f3eb5d9c1573a373aacca7466f5050ab.gif
         * urgb : #750101
         * ef : 1
         * sort : 6
         * ch : 0
         * stay_time : 200000
         * desc : 赠送网站广播并派送出神秘宝箱
         * mobile_stay_time : 15000
         * mobile_small_effect_icon : http://staticlive.douyucdn.cn/upload/dygift/1606/149b382f03945f6a0a9dbd5a67e6acc0.png
         * grgb : #a20b0b
         * drgb : #210101
         * pad_big_effect_icon : http://staticlive.douyucdn.cn/upload/dygift/1606/37a9d72b1821753fe2950ca00c8d57c9.gif
         * type : 2
         * mobimg : http://staticlive.douyucdn.cn/upload/dygift/1606/5597bc7ddb15dbba5ed41629c21442db.png
         * small_effect_icon : http://staticlive.douyucdn.cn/upload/dygift/1606/01d7d7288adfd424bdd4a6a8e8c0b9ea.png
         * name : 火箭
         * mobile_icon_v2 : http://staticlive.douyucdn.cn/upload/dygift/1606/07380f0516f12a2da3783d0c50ef664b.png
         */

        private List<GiftBean> gift;
        private List<String> cdns;

        public String getUse_p2p() {
            return use_p2p;
        }

        public void setUse_p2p(String use_p2p) {
            this.use_p2p = use_p2p;
        }

        public String getShow_details() {
            return show_details;
        }

        public void setShow_details(String show_details) {
            this.show_details = show_details;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRtmp_url() {
            return rtmp_url;
        }

        public void setRtmp_url(String rtmp_url) {
            this.rtmp_url = rtmp_url;
        }

        public String getAnchor_city() {
            return anchor_city;
        }

        public void setAnchor_city(String anchor_city) {
            this.anchor_city = anchor_city;
        }

        public String getSpecific_status() {
            return specific_status;
        }

        public void setSpecific_status(String specific_status) {
            this.specific_status = specific_status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRtmp_cdn() {
            return rtmp_cdn;
        }

        public void setRtmp_cdn(String rtmp_cdn) {
            this.rtmp_cdn = rtmp_cdn;
        }

        public String getSpecific_catalog() {
            return specific_catalog;
        }

        public void setSpecific_catalog(String specific_catalog) {
            this.specific_catalog = specific_catalog;
        }

        public int getCate_id1() {
            return cate_id1;
        }

        public void setCate_id1(int cate_id1) {
            this.cate_id1 = cate_id1;
        }

        public String getShow_status() {
            return show_status;
        }

        public void setShow_status(String show_status) {
            this.show_status = show_status;
        }

        public String getGame_icon_url() {
            return game_icon_url;
        }

        public void setGame_icon_url(String game_icon_url) {
            this.game_icon_url = game_icon_url;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getShow_time() {
            return show_time;
        }

        public void setShow_time(String show_time) {
            this.show_time = show_time;
        }

        public int getIsVertical() {
            return isVertical;
        }

        public void setIsVertical(int isVertical) {
            this.isVertical = isVertical;
        }

        public String getRtmp_live() {
            return rtmp_live;
        }

        public void setRtmp_live(String rtmp_live) {
            this.rtmp_live = rtmp_live;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public String getGame_url() {
            return game_url;
        }

        public void setGame_url(String game_url) {
            this.game_url = game_url;
        }

        public String getRoom_src() {
            return room_src;
        }

        public void setRoom_src(String room_src) {
            this.room_src = room_src;
        }

        public String getIs_white_list() {
            return is_white_list;
        }

        public void setIs_white_list(String is_white_list) {
            this.is_white_list = is_white_list;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getOwner_uid() {
            return owner_uid;
        }

        public void setOwner_uid(String owner_uid) {
            this.owner_uid = owner_uid;
        }

        public String getOwner_avatar() {
            return owner_avatar;
        }

        public void setOwner_avatar(String owner_avatar) {
            this.owner_avatar = owner_avatar;
        }

        public String getVertical_src() {
            return vertical_src;
        }

        public void setVertical_src(String vertical_src) {
            this.vertical_src = vertical_src;
        }

        public int getRoom_dm_delay() {
            return room_dm_delay;
        }

        public void setRoom_dm_delay(int room_dm_delay) {
            this.room_dm_delay = room_dm_delay;
        }

        public String getOwner_weight() {
            return owner_weight;
        }

        public void setOwner_weight(String owner_weight) {
            this.owner_weight = owner_weight;
        }

        public int getIs_pass_player() {
            return is_pass_player;
        }

        public void setIs_pass_player(int is_pass_player) {
            this.is_pass_player = is_pass_player;
        }

        public String getHls_url() {
            return hls_url;
        }

        public void setHls_url(String hls_url) {
            this.hls_url = hls_url;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getCur_credit() {
            return cur_credit;
        }

        public void setCur_credit(String cur_credit) {
            this.cur_credit = cur_credit;
        }

        public String getGift_ver() {
            return gift_ver;
        }

        public void setGift_ver(String gift_ver) {
            this.gift_ver = gift_ver;
        }

        public String getLow_credit() {
            return low_credit;
        }

        public void setLow_credit(String low_credit) {
            this.low_credit = low_credit;
        }

        public RtmpMultiBitrateBean getRtmp_multi_bitrate() {
            return rtmp_multi_bitrate;
        }

        public void setRtmp_multi_bitrate(RtmpMultiBitrateBean rtmp_multi_bitrate) {
            this.rtmp_multi_bitrate = rtmp_multi_bitrate;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getCredit_illegal() {
            return credit_illegal;
        }

        public void setCredit_illegal(String credit_illegal) {
            this.credit_illegal = credit_illegal;
        }

        public String getVod_quality() {
            return vod_quality;
        }

        public void setVod_quality(String vod_quality) {
            this.vod_quality = vod_quality;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public List<ServersBean> getServers() {
            return servers;
        }

        public void setServers(List<ServersBean> servers) {
            this.servers = servers;
        }

        public List<CdnsWithNameBean> getCdnsWithName() {
            return cdnsWithName;
        }

        public void setCdnsWithName(List<CdnsWithNameBean> cdnsWithName) {
            this.cdnsWithName = cdnsWithName;
        }

        public List<?> getBlack() {
            return black;
        }

        public void setBlack(List<?> black) {
            this.black = black;
        }

        public List<GiftBean> getGift() {
            return gift;
        }

        public void setGift(List<GiftBean> gift) {
            this.gift = gift;
        }

        public List<String> getCdns() {
            return cdns;
        }

        public void setCdns(List<String> cdns) {
            this.cdns = cdns;
        }

        public static class RtmpMultiBitrateBean {
            private String middle;
            private String middle2;

            public String getMiddle() {
                return middle;
            }

            public void setMiddle(String middle) {
                this.middle = middle;
            }

            public String getMiddle2() {
                return middle2;
            }

            public void setMiddle2(String middle2) {
                this.middle2 = middle2;
            }
        }

        public static class ServersBean {
            private String ip;
            private String port;

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getPort() {
                return port;
            }

            public void setPort(String port) {
                this.port = port;
            }
        }

        public static class CdnsWithNameBean {
            private String name;
            private String cdn;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCdn() {
                return cdn;
            }

            public void setCdn(String cdn) {
                this.cdn = cdn;
            }
        }

        public static class GiftBean {
            private String himg;
            private String pdhimg;
            private int gx;
            private String mobile_big_effect_icon_0;
            private String cimg;
            private String mobile_big_effect_icon_1;
            private String big_effect_icon;
            private String pdbimg;
            private String mobile_big_effect_icon_2;
            private String mimg;
            private String brgb;
            private String mobile_big_effect_icon_3;
            private String m_ef_gif_2;
            private String pimg;
            private String pt;
            private String id;
            private String intro;
            private String pc;
            private String m_ef_gif_1;
            private String urgb;
            private int ef;
            private String sort;
            private String ch;
            private int stay_time;
            private String desc;
            private String mobile_stay_time;
            private String mobile_small_effect_icon;
            private String grgb;
            private String drgb;
            private String pad_big_effect_icon;
            private String type;
            private String mobimg;
            private String small_effect_icon;
            private String name;
            private String mobile_icon_v2;

            public String getHimg() {
                return himg;
            }

            public void setHimg(String himg) {
                this.himg = himg;
            }

            public String getPdhimg() {
                return pdhimg;
            }

            public void setPdhimg(String pdhimg) {
                this.pdhimg = pdhimg;
            }

            public int getGx() {
                return gx;
            }

            public void setGx(int gx) {
                this.gx = gx;
            }

            public String getMobile_big_effect_icon_0() {
                return mobile_big_effect_icon_0;
            }

            public void setMobile_big_effect_icon_0(String mobile_big_effect_icon_0) {
                this.mobile_big_effect_icon_0 = mobile_big_effect_icon_0;
            }

            public String getCimg() {
                return cimg;
            }

            public void setCimg(String cimg) {
                this.cimg = cimg;
            }

            public String getMobile_big_effect_icon_1() {
                return mobile_big_effect_icon_1;
            }

            public void setMobile_big_effect_icon_1(String mobile_big_effect_icon_1) {
                this.mobile_big_effect_icon_1 = mobile_big_effect_icon_1;
            }

            public String getBig_effect_icon() {
                return big_effect_icon;
            }

            public void setBig_effect_icon(String big_effect_icon) {
                this.big_effect_icon = big_effect_icon;
            }

            public String getPdbimg() {
                return pdbimg;
            }

            public void setPdbimg(String pdbimg) {
                this.pdbimg = pdbimg;
            }

            public String getMobile_big_effect_icon_2() {
                return mobile_big_effect_icon_2;
            }

            public void setMobile_big_effect_icon_2(String mobile_big_effect_icon_2) {
                this.mobile_big_effect_icon_2 = mobile_big_effect_icon_2;
            }

            public String getMimg() {
                return mimg;
            }

            public void setMimg(String mimg) {
                this.mimg = mimg;
            }

            public String getBrgb() {
                return brgb;
            }

            public void setBrgb(String brgb) {
                this.brgb = brgb;
            }

            public String getMobile_big_effect_icon_3() {
                return mobile_big_effect_icon_3;
            }

            public void setMobile_big_effect_icon_3(String mobile_big_effect_icon_3) {
                this.mobile_big_effect_icon_3 = mobile_big_effect_icon_3;
            }

            public String getM_ef_gif_2() {
                return m_ef_gif_2;
            }

            public void setM_ef_gif_2(String m_ef_gif_2) {
                this.m_ef_gif_2 = m_ef_gif_2;
            }

            public String getPimg() {
                return pimg;
            }

            public void setPimg(String pimg) {
                this.pimg = pimg;
            }

            public String getPt() {
                return pt;
            }

            public void setPt(String pt) {
                this.pt = pt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getPc() {
                return pc;
            }

            public void setPc(String pc) {
                this.pc = pc;
            }

            public String getM_ef_gif_1() {
                return m_ef_gif_1;
            }

            public void setM_ef_gif_1(String m_ef_gif_1) {
                this.m_ef_gif_1 = m_ef_gif_1;
            }

            public String getUrgb() {
                return urgb;
            }

            public void setUrgb(String urgb) {
                this.urgb = urgb;
            }

            public int getEf() {
                return ef;
            }

            public void setEf(int ef) {
                this.ef = ef;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getCh() {
                return ch;
            }

            public void setCh(String ch) {
                this.ch = ch;
            }

            public int getStay_time() {
                return stay_time;
            }

            public void setStay_time(int stay_time) {
                this.stay_time = stay_time;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getMobile_stay_time() {
                return mobile_stay_time;
            }

            public void setMobile_stay_time(String mobile_stay_time) {
                this.mobile_stay_time = mobile_stay_time;
            }

            public String getMobile_small_effect_icon() {
                return mobile_small_effect_icon;
            }

            public void setMobile_small_effect_icon(String mobile_small_effect_icon) {
                this.mobile_small_effect_icon = mobile_small_effect_icon;
            }

            public String getGrgb() {
                return grgb;
            }

            public void setGrgb(String grgb) {
                this.grgb = grgb;
            }

            public String getDrgb() {
                return drgb;
            }

            public void setDrgb(String drgb) {
                this.drgb = drgb;
            }

            public String getPad_big_effect_icon() {
                return pad_big_effect_icon;
            }

            public void setPad_big_effect_icon(String pad_big_effect_icon) {
                this.pad_big_effect_icon = pad_big_effect_icon;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMobimg() {
                return mobimg;
            }

            public void setMobimg(String mobimg) {
                this.mobimg = mobimg;
            }

            public String getSmall_effect_icon() {
                return small_effect_icon;
            }

            public void setSmall_effect_icon(String small_effect_icon) {
                this.small_effect_icon = small_effect_icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile_icon_v2() {
                return mobile_icon_v2;
            }

            public void setMobile_icon_v2(String mobile_icon_v2) {
                this.mobile_icon_v2 = mobile_icon_v2;
            }
        }
    }
}
