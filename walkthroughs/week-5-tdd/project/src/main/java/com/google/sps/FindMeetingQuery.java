// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.sps;

import java.util.*;
import java.lang.*;

public final class FindMeetingQuery {
    int MAX_TIME = 24 * 60;

    public Collection < TimeRange > query(Collection < Event > events, MeetingRequest request) {
        Collection < String > meetAttendees = request.getAttendees();
        Collection < String > optionalAttendees = new ArrayList();
        try {
            optionalAttendees = request.getOptionalAttendees();
        } catch (Exception e) {
            //   error
        }

        HashMap < Integer, ArrayList < TimeRange >> optionalAttendeesFreq = new HashMap();
        HashMap < Integer, Integer > optAttendeesTime = new HashMap();
        long duration = request.getDuration();
        int totalOptAttendees = optionalAttendees.size();
        int prev = totalOptAttendees;
        int max = Integer.MIN_VALUE;
        int lastEventMandAttendeeBusy = 0;
        int lastEvent = 0;
        optAttendeesTime.put(0, 0);
        for (Event event: events) {
            int curr = 0;
            Set < String > attendees = event.getAttendees();
            TimeRange time = event.getWhen();

            // include gaps into your answer
            if (time.start() != lastEvent) {
                optAttendeesTime.put(totalOptAttendees, lastEvent);
                prev = totalOptAttendees;
            }

            // check if all mendatory attendees can attend the meeting
            boolean flag = check(meetAttendees, attendees);
            //  how many optional attendees will be busy
            for (String attendee: optionalAttendees) {
                curr++;
            }
            //  how many optional attendees will be free
            curr = totalOptAttendees - curr;

            if (flag) {
                for (Map.Entry < Integer, Integer > freq: optAttendeesTime.entrySet()) {
                    int lowerLimit = freq.getValue();
                    if (time.start() - lowerLimit >= duration) {
                        TimeRange emptySlot = TimeRange.fromStartEnd(lowerLimit, time.start(), false);
                        add(optionalAttendeesFreq, freq.getKey(), emptySlot);
                        max = Math.max(max, freq.getKey());
                    }
                }
                optAttendeesTime = new HashMap();
                prev = 0;
                lastEvent = Math.max(lastEvent, time.end());
                lastEventMandAttendeeBusy = Math.max(lastEventMandAttendeeBusy, time.end());
            } else if (curr < prev) {
                for (Map.Entry < Integer, Integer > freq: optAttendeesTime.entrySet()) {
                    if (freq.getKey() <= curr) {
                        break;
                    }
                    int lowerLimit = freq.getValue();
                    if (time.start() - lowerLimit >= duration) {
                        TimeRange emptySlot = TimeRange.fromStartEnd(lowerLimit, time.start(), false);
                        add(optionalAttendeesFreq, freq.getKey(), emptySlot);
                        max = Math.max(max, freq.getKey());
                    }
                }
                optAttendeesTime = new HashMap();
                optAttendeesTime.put(curr, time.start());
                prev = curr;
                lastEvent = time.end();
            } else if (curr > prev) {
                optAttendeesTime.put(curr, time.start());
                prev = curr;
                lastEvent = time.end();
            }
        }

        // if all the events end early in the evening then meeting can be scheduled later in evening
        if (MAX_TIME - lastEventMandAttendeeBusy >= duration) {
            if (MAX_TIME - lastEvent >= duration)
                optAttendeesTime.put(totalOptAttendees, lastEvent);
            for (Map.Entry < Integer, Integer > freq: optAttendeesTime.entrySet()) {
                int lowerLimit = freq.getValue();
                if (MAX_TIME - lowerLimit >= duration) {
                    TimeRange emptySlot = TimeRange.fromStartEnd(lowerLimit, MAX_TIME, false);
                    add(optionalAttendeesFreq, freq.getKey(), emptySlot);
                    max = Math.max(max, freq.getKey());
                }
            }
        }

        // if no meeting can't be scheduled return empty list else return answer
        if (optionalAttendeesFreq.size() == 0) {
            return new ArrayList();
        } else {
            return optionalAttendeesFreq.get(max);
        }

    }

    //   add empty time slot to frequency map
    public void add(HashMap < Integer, ArrayList < TimeRange >> optionalAttendeesFreq, int freq, TimeRange emptySlot) {

        if (optionalAttendeesFreq.containsKey(freq)) {
            ArrayList < TimeRange > availableSlots = optionalAttendeesFreq.get(freq);
            availableSlots.add(emptySlot);
        } else {
            ArrayList < TimeRange > availableSlots = new ArrayList();
            availableSlots.add(emptySlot);
            optionalAttendeesFreq.put(freq, availableSlots);
        }

    }
    // check if all mendatory attendees can attend the meeting
    public boolean check(Collection < String > meetAttendees, Set < String > attendees) {
        for (String attendee: meetAttendees) {
            if (attendees.contains(attendee)) {
                return true;
            }
        }
        return false;
    }


}