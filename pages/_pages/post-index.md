---
title: Post Index
author: rdd13r
layout: page
permalink: /list/
---

{% for tag in site.tags %}
<h3>{{ tag[0] }}</h3>
  <ul>
    {% for post in tag[1] %}
      <li><!--suppress HtmlUnknownTarget -->
        <a href="{{ post.url | relative_url }}">{{ post.title }}</a> (
        {% for cat in post.categories %}
            {{ cat }},&nbsp;
        {% endfor %}
        )
      </li>
    {% endfor %}
  </ul>
{% endfor %}

