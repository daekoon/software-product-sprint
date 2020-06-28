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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<Event> existingEvents = new ArrayList<Event>(events);
    List<TimeRange> possibleSchedule = new ArrayList<TimeRange>();

    Collections.sort(existingEvents);
    int curStartTime = 0;
    TimeRange eventTimeRange;
    boolean contains;

    for (Event event : existingEvents) {
      if (Collections.disjoint(event.getAttendees(), request.getAttendees())) {
        continue;
      }
      
      eventTimeRange = event.getWhen();

      if (eventTimeRange.start() <= curStartTime) {
        if (eventTimeRange.end() <= curStartTime) {
          continue;
        }
        curStartTime = eventTimeRange.end();
        continue;
      }

      addToScheduleIfMeetingFitsBeforeEvent(possibleSchedule, request, curStartTime, eventTimeRange);
      curStartTime = eventTimeRange.end();
    }

    addToScheduleIfMeetingFitsAtEndOfDay(possibleSchedule, request, curStartTime);

    return possibleSchedule;
  }

  private void addToScheduleIfMeetingFitsBeforeEvent(
      List<TimeRange> possibleSchedule,
      MeetingRequest request,
      int curStartTime,
      TimeRange eventTimeRange) {

    if (eventTimeRange.start() - curStartTime >= request.getDuration()) {
      possibleSchedule.add(TimeRange.fromStartDuration(curStartTime, eventTimeRange.start() - curStartTime));
    }
  }

  private void addToScheduleIfMeetingFitsAtEndOfDay(List<TimeRange> possibleSchedule, MeetingRequest request, int curStartTime) {
    if (curStartTime != TimeRange.END_OF_DAY) {
      if (TimeRange.END_OF_DAY - curStartTime >= request.getDuration()) {
        possibleSchedule.add(TimeRange.fromStartDuration(curStartTime, TimeRange.END_OF_DAY - curStartTime + 1));
      }
    }
  }
}
