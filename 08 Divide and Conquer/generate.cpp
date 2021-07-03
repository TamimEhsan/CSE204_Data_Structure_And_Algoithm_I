#include<bits/stdc++.h>
using namespace std;
#define x first
#define y second

mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());
int my_rand(int l, int r){return uniform_int_distribution<int>(l,r) (rng);}

long double calculateDistance(pair<int,int> &A,pair<int,int> &B){
    int val = (A.x-B.x)*(A.x-B.x) + (A.y-B.y)*(A.y-B.y);
    return sqrt(val);
}

vector<pair<int,int>> values;
long double mn,ss;
void generateValues(int n){
    map<pair<int,int>,int> m;
    for(int i=0;i<n;i++){
        pair<int,int> p ;
        p.x= my_rand(-10000,10000);
        p.y = my_rand(-10000,10000) ;
        if( m[p] == 0 ){
            m[p] = 1;
            values.push_back(p);
        }

    }
    long double mnDis = calculateDistance(values[0],values[1]);
    for(int i=0;i<values.size();i++){
        for(int j=i+1;j<values.size();j++){
            mnDis = min(calculateDistance(values[i],values[j]) , mnDis );
        }
    }
    int one = 0;
    for(int i=0;i<values.size();i++){
        for(int j=i+1;j<values.size();j++){
            if( calculateDistance(values[i],values[j]) == mnDis ){
                cout<<"fsfsdf ";
                if( one == 0 ){
                    one = 1;
                }else {
                    swap( values[j],values.back() );
                    values.pop_back();
                }
            }
        }
    }


    mn = ss = 2147483647.00;
    for(int i=0;i<values.size();i++){
        for(int j=i+1;j<values.size();j++){
            long double dis = calculateDistance(values[i],values[j]) ;
            if( dis<mn ){
                ss = mn;
                mn = dis;
            }else if( dis>mn and dis<ss ){
                ss = mn;
            }
        }
    }

}

int main(){
    ofstream fout;
    fout.open("test2.txt");
    int n = 10000;
    generateValues(n);
    fout<<values.size()<<endl;
    for(auto [a,b]:values) fout<<a<<" "<<b<<endl;

    cout<<values.size();
    fout<<endl<<endl;
    fout<<mn<<" "<<ss<<endl;
}
