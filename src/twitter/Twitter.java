/**
 * @author Abhinandan Tumkur Umesha
 */

package twitter;

import java.util.*;

public class Twitter {
    /** Initialize your data structure here. */
    HashMap<Integer,HashSet<Integer>> followsMap = new  HashMap<Integer,HashSet<Integer>>();
    HashMap<Integer,TweetNode> tweetsMap = new  HashMap<Integer,TweetNode>();
    long numTweet = 0;
   
    public Twitter() {
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(tweetsMap.containsKey(userId)){
            tweetsMap.put(userId,new TweetNode(tweetId,++numTweet,tweetsMap.get(userId)));
        }
        else{
            TweetNode aTweetNode = new TweetNode(tweetId,++numTweet,null);
            tweetsMap.put(userId,aTweetNode);
        }
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
             
            List<Integer> mostRecentTweets = new ArrayList<Integer>();
            
            PriorityQueue<TweetNode> queue = new PriorityQueue<TweetNode>(new Comparator<TweetNode>(){
                public int compare(TweetNode tweet1, TweetNode tweet2){
                    return (int) -(tweet1.tweetTime - tweet2.tweetTime);
                }
            });
            
            if(tweetsMap.containsKey(userId))
                queue.add(tweetsMap.get(userId));
                
            if(followsMap.containsKey(userId)){               
                for(int followee : followsMap.get(userId)){
                    if(tweetsMap.containsKey(followee)){
                        queue.add(tweetsMap.get(followee));
                    }
                }
            }
            
            int count = 0;
            while(!queue.isEmpty() && count < 10){
                TweetNode aTweetNode = queue.poll();
                mostRecentTweets.add(aTweetNode.tweetId);
                if(aTweetNode.next != null)
                    queue.add(aTweetNode.next);               
                count++;
            }
        
            return mostRecentTweets;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {      
        if(followerId == followeeId) return;
        if(followsMap.containsKey(followerId)){
            followsMap.get(followerId).add(followeeId);
        }
        else{
            HashSet<Integer> followeeMap = new  HashSet<Integer>();
            followeeMap.add(followeeId);
            followsMap.put(followerId, followeeMap);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followsMap.containsKey(followerId)){
            if(followsMap.get(followerId).contains(followeeId)){
                followsMap.get(followerId).remove(followeeId);
            }
        }
    }
}

class TweetNode {
    public int tweetId;
    public long tweetTime;
    public TweetNode next;
    
    public TweetNode(int tweetId, long tweetTime, TweetNode next){
        this.tweetId = tweetId;
        this.tweetTime = tweetTime;
        this.next = next;
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */

