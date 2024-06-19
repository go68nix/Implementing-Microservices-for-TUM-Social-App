package eist24l03p03.activitymicroservice.Controller;

import eist24l03p03.activitymicroservice.FollowRequest;
import eist24l03p03.activitymicroservice.Tweet;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import eist24l03p03.activitymicroservice.User;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    // TODO: implement the methods of this class. Make a use of the provided data structures
    private Map<Integer, List<Tweet>> userActivityMap = new HashMap<>();
    private Map<Integer, List<User>> userFollowedMap = new HashMap<>();
    @GetMapping("/getActivity/{id}")
    public List<Tweet> getActivity(@PathVariable("id") int userID) {
        List<Tweet> defaultTweetList = new ArrayList<>();
        if(userActivityMap.containsKey(userID)){
            return userActivityMap.get(userID);
        }
        return defaultTweetList;
    }
    @PostMapping("/addActivity")
    public void addActivity(@RequestBody Tweet tweet) {
        if(!userActivityMap.containsKey(tweet.getUser().getUserID())){
            List<Tweet> a=new ArrayList<>();
            a.add(tweet);
            userActivityMap.put(tweet.getUser().getUserID(),a);
            return;
        }
        userActivityMap.get(tweet.getUser().getUserID()).add(tweet);

    }
    @PostMapping("/addFollower")
    public void addFollower(@RequestBody FollowRequest followRequest) {
        if(!userFollowedMap.containsKey(followRequest.getFollower().getUserID())){
            List<User> a=new ArrayList<>();
            a.add(followRequest.getFollowed());
            userFollowedMap.put(followRequest.getFollower().getUserID(),a);
        }
        userFollowedMap.get(followRequest.getFollower().getUserID()).add(followRequest.getFollowed());
    }
    @DeleteMapping("/deleteActivity")
    public void deleteActivity(@RequestBody Tweet tweet) {
        if(userActivityMap.containsKey(tweet.getUser().getUserID())){
            for(Tweet a:userActivityMap.get(tweet.getUser().getUserID())){
                if(a.getTweetID()==tweet.getTweetID()){
                    userActivityMap.get(tweet.getUser().getUserID()).remove(tweet);
                    return;
                }
            }

        }
    }
    @DeleteMapping("/deleteFollower")
    public void deleteFollower(@RequestBody FollowRequest followRequest) {
        if(userFollowedMap.containsKey(followRequest.getFollower().getUserID())){
            for(User a:userFollowedMap.get(followRequest.getFollower().getUserID())){
                if(a.getUserID()==followRequest.getFollowed().getUserID()){
                    userFollowedMap.get(followRequest.getFollower().getUserID()).remove(a);
                    return;

                }
            }
        }

    }
    @GetMapping("/getFollowedList/{id}")
    public List<User> getFollowedList(@PathVariable("id") int userID) {
        // TODO: this method is also not complete, so you have to implement it accordingly :)
        List<User> defaultUserList = new ArrayList<>();
        if(userFollowedMap.containsKey(userID)){
            return userFollowedMap.get(userID);
        }
        return defaultUserList;
    }
}