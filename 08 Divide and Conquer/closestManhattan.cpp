#include<bits/stdc++.h>
#include "2ndclosestManhattan.h"
using namespace std;
#define INF 2147483647
/*
struct Point{
    int x;
    int y;
    int index;
};
struct Solution{
    int distance;
    int index1;
    int index2;
};

int calculateDistance(Point &A,Point &B){
    return (A.x-B.x)*(A.x-B.x) + (A.y-B.y)*(A.y-B.y);
}
bool compX(Point &A,Point &B){
    if( A.x!=B.x ) return A.x<B.x;
    return A.y<B.y;
}
bool compY(Point &A,Point &B){
    if( A.y!=B.y ) return A.y<B.y;
    return A.x<B.x;
}*/

Solution mini(Solution A,Solution B){
    if( A.distance<B.distance ) return A;
    else return B;
}

Solution stripDistance(vector<Point> strip,int distance){
    Solution solution = {INF,-1,-1};
    for(int i=0;i<strip.size();i++){
        for(int j=i+1;j<strip.size() and strip[j].y-strip[i].y<distance;j++){
            int strpDis = calculateDistance(strip[i],strip[j]);
            if( strpDis < distance ){
                distance = strpDis;
                solution = {distance,strip[i].index,strip[j].index};
            }
        }
    }
    return solution;
}

Solution findClosestPair(Point pts[],int left,int right){
    if( left>=right ) return {INF,-1,-1};
    int mid = (left+right)/2;
    Solution solutionLeft = findClosestPair(pts,left,mid); // We are considering the points on the mid as the left seg
    Solution solutionRight = findClosestPair(pts,mid+1,right);
    Solution minimum = mini(solutionLeft,solutionRight);
    int distance = minimum.distance;
    vector<Point> strip;
    strip.push_back(pts[mid]);
    for(int i=mid+1;i<=right;i++){
        if( pts[i].x-pts[mid].x>distance ) break;
        strip.push_back(pts[i]);
    }
    for(int i=mid-1;i>=left;i--){
        if( pts[mid].x-pts[i].x>distance ) break;
        strip.push_back(pts[i]);
    }
    sort(strip.begin(),strip.end(),compY);
    return mini(minimum,stripDistance(strip,distance));
}

int main(){
    cout<<fixed<<setprecision(4)<<endl;
    int n;
    cin>>n;
    Point pts[n];
    for(int i=0;i<n;i++) {
        //cin>>pts[i].x>>pts[i].y;
        pts[i].x = my_rand(-10000,10000);
        pts[i].y = my_rand(-10000,10000);
        pts[i].index = i;
    }
    best = {INF,-1,-1};
    second_best = {INF,-1,-1};

    sort(pts,pts+n,compX);
    auto start = chrono::high_resolution_clock::now();
    findClosestPair2(pts,0,n-1);
    long double time_taken = chrono::duration_cast<chrono::milliseconds>(chrono::high_resolution_clock::now() - start).count();
    cout<<second_best.index1<<" "<<second_best.index2<<" "<<sqrt(second_best.distance)<<" "<<sqrt(best.distance)<<" "<<time_taken<<endl;

    start = chrono::high_resolution_clock::now();

    Solution distanceMain = findClosestPair(pts,0,n-1);

    for(int i=0;i<n;i++){
        if( distanceMain.index1 == pts[i].index ) swap( pts[i],pts[n-1] );
    }
    sort(pts,pts+n-1,compX);
    Solution distance1 = findClosestPair(pts,0,n-2);

    for(int i=0;i<n-1;i++){
        if( distanceMain.index2 == pts[i].index ) swap( pts[i],pts[n-1] );
    }
    sort(pts,pts+n-1,compX);
    Solution distance2 = findClosestPair(pts,0,n-2);

    time_taken = chrono::duration_cast<chrono::milliseconds>(chrono::high_resolution_clock::now() - start).count();

    if( distance1.distance<distance2.distance ){
        cout<<distance1.index1<<" "<<distance1.index2<<endl<<sqrt(distance1.distance);
    }else{
        cout<<distance2.index1<<" "<<distance2.index2<<endl<<sqrt(distance2.distance);
    }
    cout<<endl<<time_taken<<endl;

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
