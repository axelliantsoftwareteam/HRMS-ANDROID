package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.SearchView;

import com.example.hrm.Adapter.ClubAdapter;
import com.example.hrm.Model.Club;
import com.example.hrm.R;
import com.example.hrm.utils.FixedGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    int scrollX = 0;

    List<Club> clubList = new ArrayList<>();

    RecyclerView rvClub;

    HorizontalScrollView headerScroll;

    SearchView searchView;

    ClubAdapter clubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        initViews();

        prepareClubData();

        setUpRecyclerView();

        rvClub.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;

                headerScroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
    private void initViews()
    {
        rvClub = findViewById(R.id.rvClub);
        headerScroll = findViewById(R.id.headerScroll);
    }

    /**
     * Prepares dummy data
     */
    private void prepareClubData()
    {
        clubList.add(new Club("Galatasaray", "https://tmssl.akamaized.net/images/wappen/head/141.png", "Istanbul, Turkey", "Ali Sami Yen", "Süper Lig", "Fatih Terim", "Bafetimbi Gomis"));
        clubList.add(new Club("Real Madrid", "https://tmssl.akamaized.net//images/wappen/head/418.png", "Madrid, Spain", "Santiago Barnabeu", "La Liga", "Zidane", "Cristiano Ronaldo"));
        clubList.add(new Club("Barcelona", "https://tmssl.akamaized.net//images/wappen/head/131.png", "Barcelona, Spain", "Camp Nou", "La Liga", "Ernesto Valverde", "Lionel Messi"));
        clubList.add(new Club("Bayern München", "https://tmssl.akamaized.net//images/wappen/head/27.png", "München, Germany", "Allianz Arena", "Bundesliga", "Jupp Heynckes", "Robert Lewandowski"));
        clubList.add(new Club("Manchester United", "https://tmssl.akamaized.net//images/wappen/head/985.png", "Manchester, England", "Old Trafford", "Premier League", "Jose Mourinho", "Paul Pogba"));
        clubList.add(new Club("Manchester City", "https://tmssl.akamaized.net//images/wappen/head/281.png", "Manchester, England", " Etihad Stadium", "Premier League", "Pep Guardiola", "Kevin de Bruyne"));
        clubList.add(new Club("Atletico Madrid", "https://tmssl.akamaized.net//images/wappen/head/13.png", "Madrid, Spain", "Estadio Metropolitano de Madrid ", "La Liga", "Diego Simeone", "Antoine Griezmann"));
        clubList.add(new Club("Liverpool", "https://tmssl.akamaized.net//images/wappen/head/31.png", "Liverpool, Spain", "Anfield", "Premier League", "Klopp", "Mo Salah"));
        clubList.add(new Club("Juventus", "https://tmssl.akamaized.net//images/wappen/head/506.png", "Turin, Italy", "Allianz Stadium", "Serie A", "Massimiliano Allegri", "Paulo Dybala"));
        clubList.add(new Club("Arsenal", "https://tmssl.akamaized.net//images/wappen/head/11.png", "London, England", "Emirates Stadium", "Premier League", "Arsene Wenger", "Mesut Özil"));
        clubList.add(new Club("Roma", "https://tmssl.akamaized.net//images/wappen/head/12.png", "Rome, Italy", " Olimpico di Roma", "Serie A", "Eusebio Di Francesco", "Cengiz Ünder"));
        clubList.add(new Club("PSG", "https://tmssl.akamaized.net//images/wappen/head/583.png", "Paris, France", "Parc des Princes ", "Ligue 1", "Unai Emery", "Neymar"));
        clubList.add(new Club("Chelsea", "https://tmssl.akamaized.net//images/wappen/head/631.png", "London, England", "Stamford Bridge", "Premier League", "Conte", "Eden Hazard"));
        clubList.add(new Club("Tottenham", "https://tmssl.akamaized.net//images/wappen/head/148.png", "London, England", "Wembley Stadium ", "Premier League", "Mauricio Pochettino", "Harry Kane"));
    }

    /**
     * Handles RecyclerView for the action
     */
    private void setUpRecyclerView()
    {
        clubAdapter = new ClubAdapter(TableActivity.this, clubList);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(clubAdapter);
        rvClub.addItemDecoration(new DividerItemDecoration(TableActivity.this, DividerItemDecoration.VERTICAL));
    }
}