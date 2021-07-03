#include<bits/stdc++.h>
using namespace std;
#define INF 2147483647


mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());
int my_rand(int l, int r){return uniform_int_distribution<int>(l,r) (rng);}



struct Point{
    int x;
    int y;
    int index;
};

struct Solution{
    long double distance;
    int index1;
    int index2;
}best,second_best;

long double calculateDistance(Point &A,Point &B){
    int val = (A.x-B.x)*(A.x-B.x) + (A.y-B.y)*(A.y-B.y);
    return sqrt(val);
}
bool compX(Point &A,Point &B){
    if( A.x!=B.x ) return A.x<B.x;
    return A.y<B.y;
}
bool compY(Point &A,Point &B){
    if( A.y!=B.y ) return A.y<B.y;
    return A.x<B.x;
}


void update(Point A,Point B){
    long double distance = calculateDistance(A,B);
    if( A.index>B.index ) swap(A,B);
    if( distance<best.distance ){
        second_best = best;
        best = {distance,A.index,B.index};
    }else if( distance<second_best.distance and distance>best.distance ){
        second_best = {distance,A.index,B.index};
    }
}
void stripDistance(vector<Point> strip){
    for(int i=0;i<strip.size();i++)
        for(int j=i+1;j<strip.size() and strip[j].y-strip[i].y<second_best.distance;j++)
            update(strip[i],strip[j]);

}

void findClosestPair2(Point pts[],int left,int right){
    if( left>=right ) return ;
    int mid = (left+right)/2;
    findClosestPair2(pts,left,mid); // We are considering the points on the mid as the left seg
    findClosestPair2(pts,mid+1,right);

    vector<Point> strip;
    strip.push_back(pts[mid]);
    for(int i=mid+1;i<=right;i++){
        if( pts[i].x-pts[mid].x>second_best.distance ) break;
        strip.push_back(pts[i]);
    }
    for(int i=mid-1;i>=left;i--){
        if( pts[mid].x-pts[i].x>second_best.distance ) break;
        strip.push_back(pts[i]);
    }
    sort(strip.begin(),strip.end(),compY);
    stripDistance(strip);
}

int main(){

    int n=0;
    vector<int> vals;
    cin>>n;
    Point pts[n];
    for(int i=0;i<n;i++) {
        cin>>pts[i].x>>pts[i].y;
        //pts[i] = {my_rand(-10000,10000),my_rand(-10000,10000),i};
        pts[i].index = i;
    }
    cout<<endl<<endl;
    best = {INF,-1,-1};
    second_best = {INF,-1,-1};
    sort(pts,pts+n,compX);
    findClosestPair2(pts,0,n-1);
    cout<<fixed<<setprecision(4)<<second_best.index1<<" "<<second_best.index2<<"\n"<<second_best.distance<<endl;
}


/*
10
-2289 9295
3137 3748
-5033 5679
7729 -432
9262 16
8341 -5462
-4163 3577
1710 -3328
-8676 -3161
5106 3295
*/
/*
5
-9765 2172
-4808 7289
955 -2187
9279 -9856
6332 -2249
*/
