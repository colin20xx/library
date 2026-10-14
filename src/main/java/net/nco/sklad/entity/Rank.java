package net.nco.sklad.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ranks", schema = "sklad")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rank", nullable=false)
    private int idRank;

    @Column(name = "rank_title", unique=true, nullable=false)
    private String rankTitle;

    public int getIdRank() {
        return idRank;
    }

    public void setIdRank(int idRank) {
        this.idRank = idRank;
    }

    public String getRankTitle() {
        return rankTitle;
    }

    public void setRankTitle(String rankTitle) {
        this.rankTitle = rankTitle;
    }
}
