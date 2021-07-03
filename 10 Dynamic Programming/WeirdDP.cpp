#include<bits/stdc++.h>
using namespace std;
#define INF 2147483647
#define INFL 9223372036854775807
#define pii pair<int,int>
#define F first
#define S second
#define mp make_pair
#define pb push_back
#define ll long long
#define ull unsigned long long
#define M 1000'000'007
#define FASTIO ios_base::sync_with_stdio(false);cin.tie(NULL); cout.tie(NULL);
#define take(x) scanf("%d",&x)
#define DE(x) printf("\ndebug %d\n",x);
#define vout(x) for(int i=0;i<x.size();i++) printf("%d ",x[i]);
#define pie acos(-1)
#define eps 1e-7
//#define MOD 998244353
#define FILEOUT {ofstream cout; cout.open ("example.txt");}


int Set(int N,int pos){return N=N | (1<<pos);}
int reset(int N,int pos){return N= N & ~(1<<pos);}
bool check(int N,int pos){return (bool)(N & (1<<pos));}




int main(){

    //ifstream cin; cin.open ("qv2.txt");
    int n,x;
    cin>>n>>x;
    int faces[n];
    for(int i=0;i<n;i++)
        cin>>faces[i];
    ll table[x+5];
    ll temp[x+5];

    memset(table, 0, sizeof(table));
    table[0] = 1;
	for (int i = 1; i <= n; i++){
        memset(temp, 0, sizeof(temp));
        for (int j = 1; j <= x; j++){
            if( j-faces[i-1]-1<0 ) temp[j] = table[j-1];
            else temp[j] = ( table[j-1] - table[j-faces[i-1]-1]+ M )%M;
        }
        table[0] = temp[0];
        for(int i=1;i<=x;i++) table[i] = (temp[i]+table[i-1])%M;
        cout<<i<<" ";
	}

    cout<<table[x]<<endl;

}





